package astrotibs.villagenames.integration.antiqueatlas.signposts;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.FunctionsVN.VillageType;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;

// --- Sign Post ---//

public class SignPost extends StructureVillageVN.VNComponent {

    public static final String SIGNPOST_MARKER = "signPost";

    // Make foundation with blanks as empty air and F as foundation spaces
    private static String[] foundationPattern = new String[] { "FFFFF", "FFFFF", "FFFFF", "FFFFF", };

    // Here are values to assign to the bounding box
    public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    public static final int STRUCTURE_DEPTH = foundationPattern.length;
    public static final int STRUCTURE_HEIGHT = 4;
    // Values for lining things up
    public static final int GROUND_LEVEL = 2; // Spaces above the bottom of the structure considered to be "ground
                                              // level"
    private static final int INCREASE_MIN_U = 0;
    private static final int DECREASE_MAX_U = 0;
    private static final int INCREASE_MIN_W = 0;
    private static final int DECREASE_MAX_W = 0;

    public SignPost() {}

    public SignPost(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox,
        int coordBaseMode) {
        super();
        this.coordBaseMode = coordBaseMode;
        this.boundingBox = boundingBox;
        // Additional stuff to be used in the construction
        this.ascertainVillageStatsFromStartPiece(start);
    }

    public static SignPost buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z,
        int coordBaseMode, int componentType) {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(
            x,
            y,
            z,
            0,
            0,
            0,
            STRUCTURE_WIDTH,
            STRUCTURE_HEIGHT,
            STRUCTURE_DEPTH,
            coordBaseMode);

        return canVillageGoDeeper(structureboundingbox)
            && StructureComponent.findIntersecting(pieces, structureboundingbox) == null
                ? new SignPost(villagePiece, componentType, random, structureboundingbox, coordBaseMode)
                : null;
    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(
                world,
                // Set the bounding box version as this bounding box but with Y going from 0 to 512
                new StructureBoundingBox(
                    this.boundingBox.minX + (new int[] { INCREASE_MIN_U, DECREASE_MAX_W, INCREASE_MIN_U,
                        INCREASE_MIN_W }[this.coordBaseMode]),
                    this.boundingBox.minZ + (new int[] { INCREASE_MIN_W, INCREASE_MIN_U, DECREASE_MAX_W,
                        INCREASE_MIN_U }[this.coordBaseMode]),
                    this.boundingBox.maxX - (new int[] { DECREASE_MAX_U, INCREASE_MIN_W, DECREASE_MAX_U,
                        DECREASE_MAX_W }[this.coordBaseMode]),
                    this.boundingBox.maxZ - (new int[] { DECREASE_MAX_W, DECREASE_MAX_U, INCREASE_MIN_W,
                        DECREASE_MAX_U }[this.coordBaseMode])),
                true,
                (byte) 15,
                this.coordBaseMode);

            if (this.averageGroundLevel < 0) {
                return true;
            } // Do not construct in a void

            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
        }

        // In the event that this village construction is resuming after being unloaded
        // you may need to reestablish the village name/color/type info
        this.populateVillageFields(world);

        // Establish blocks
        Object[] blockObject;

        // Grass
        blockObject = StructureVillageVN
            .getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs);
        Block biomeGrassBlock = (Block) blockObject[0];
        int biomeGrassMeta = (Integer) blockObject[1];
        // Dirt
        blockObject = StructureVillageVN
            .getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs);
        Block biomeDirtBlock = (Block) blockObject[0];
        int biomeDirtMeta = (Integer) blockObject[1];
        // Establish top and filler blocks, substituting Grass and Dirt if they're null
        Block biomeTopBlock = biomeGrassBlock;
        int biomeTopMeta = biomeGrassMeta;
        if (this.biome != null && this.biome.topBlock != null) {
            biomeTopBlock = this.biome.topBlock;
            biomeTopMeta = 0;
        }
        Block biomeFillerBlock = biomeDirtBlock;
        int biomeFillerMeta = biomeDirtMeta;
        if (this.biome != null && this.biome.fillerBlock != null) {
            biomeFillerBlock = this.biome.fillerBlock;
            biomeFillerMeta = 0;
        }

        // Cobblestone
        blockObject = StructureVillageVN
            .getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs);
        Block biomeCobblestoneBlock = (Block) blockObject[0];
        int biomeCobblestoneMeta = (Integer) blockObject[1];
        // Cobblestone wall
        blockObject = StructureVillageVN.getBiomeSpecificBlockObject(
            Blocks.cobblestone_wall,
            0,
            this.materialType,
            this.biome,
            this.disallowModSubs);
        Block biomeCobblestoneWallBlock = (Block) blockObject[0];
        int biomeCobblestoneWallMeta = (Integer) blockObject[1];
        // Vertical log
        blockObject = StructureVillageVN
            .getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs);
        Block biomeLogVertBlock = (Block) blockObject[0];
        int biomeLogVertMeta = (Integer) blockObject[1];
        // Vertical stripped log
        blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0);
        Block biomeStrippedLogVerticBlock = (Block) blockObject[0];
        int biomeStrippedLogVerticMeta = (Integer) blockObject[1];
        // Fence
        blockObject = StructureVillageVN
            .getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs);
        Block biomeFenceBlock = (Block) blockObject[0];
        int biomeFenceMeta = (Integer) blockObject[1];

        // Clear space above
        this.clearSpaceAbove(world, structureBB, this.STRUCTURE_WIDTH, this.STRUCTURE_DEPTH, this.GROUND_LEVEL);

        // Determine the position of the sign post
        int signPostXBB = 2;
        int signPostYBB = GROUND_LEVEL;
        int signPostZBB = 1;

        int signX = this.getXWithOffset(signPostXBB, signPostZBB);
        int signY = this.getYWithOffset(signPostYBB);
        int signZ = this.getZWithOffset(signPostXBB, signPostZBB);

        // Make it surrounded by path sometimes
        switch (this.villageType) {
            default:
            case PLAINS:
            case TAIGA:
            case SAVANNA:
            case SNOWY:
            case JUNGLE:
                if (random.nextBoolean()) {
                    foundationPattern = new String[] { "FFFFF", "FPPPF", "FPFPF", "FPPPF", };
                }
                break;
            case DESERT:
                foundationPattern = new String[] { "FFFFF", "FPPPF", "FPFPF", "FPPPF", };
                break;
            case SWAMP:
                foundationPattern = new String[] { "     ", " PPP ", " PFP ", " PPP ", };
                break;
        }

        // Follow the blueprint to set up the starting foundation
        this.establishFoundation(
            world,
            structureBB,
            this.foundationPattern,
            this.GROUND_LEVEL,
            this.materialType,
            this.disallowModSubs,
            this.biome,
            biomeTopBlock,
            biomeTopMeta,
            biomeFillerBlock,
            biomeFillerMeta,
            false);

        int signPostX = this.getXWithOffset(signPostXBB, signPostZBB);
        int signPostY = this.getYWithOffset(signPostYBB);
        int signPostZ = this.getZWithOffset(signPostXBB, signPostZBB);

        NBTTagCompound villageNBTtag = StructureVillageVN
            .getOrMakeVNInfo(world, this.getXWithOffset(4, 4), this.getYWithOffset(12), this.getZWithOffset(4, 4));

        // Load the values of interest into memory
        if (this.townColor == -1) {
            this.townColor = villageNBTtag.getInteger("townColor");
        }
        if (this.townColor2 == -1) {
            this.townColor2 = villageNBTtag.getInteger("townColor2");
        }
        if (this.townColor3 == -1) {
            this.townColor3 = villageNBTtag.getInteger("townColor3");
        }
        if (this.townColor4 == -1) {
            this.townColor4 = villageNBTtag.getInteger("townColor4");
        }
        if (this.townColor5 == -1) {
            this.townColor5 = villageNBTtag.getInteger("townColor5");
        }
        if (this.townColor6 == -1) {
            this.townColor6 = villageNBTtag.getInteger("townColor6");
        }
        if (this.townColor7 == -1) {
            this.townColor7 = villageNBTtag.getInteger("townColor7");
        }
        if (this.namePrefix.equals("")) {
            this.namePrefix = villageNBTtag.getString("namePrefix");
        }
        if (this.nameRoot.equals("")) {
            this.nameRoot = villageNBTtag.getString("nameRoot");
        }
        if (this.nameSuffix.equals("")) {
            this.nameSuffix = villageNBTtag.getString("nameSuffix");
        }

        // Do biome-based randomization

        final String NOTHING = "nothing";
        final String COBBLESTONE_BLOCK = "cobblestone block";
        final String COBBLESTONE_WALL = "cobblestone wall";
        final String STRIPPED_LOG = "stripped log";
        final String FENCE = "fence";

        // What the sign is resting on
        String signIsOn;
        int random_pick = random.nextInt(10);
        switch (this.villageType) {
            default: // NOTHING, COBBLESTONE, C. WALL, STRIPPED LOG
            case PLAINS: // 6 2 1 1
            case SNOWY: // 6 2 1 1
            case JUNGLE: // 6 2 1 1
                signIsOn = random_pick < 6 ? NOTHING
                    : random_pick < 6 + 2 ? COBBLESTONE_BLOCK
                        : random_pick < 6 + 2 + 1 ? COBBLESTONE_WALL : STRIPPED_LOG;
                break;
            case DESERT: // 2 8 0 0
                signIsOn = random_pick < 2 ? NOTHING : COBBLESTONE_BLOCK;
                break;
            case TAIGA: // 2 3 2 3
                signIsOn = random_pick < 2 ? NOTHING
                    : random_pick < 2 + 3 ? COBBLESTONE_BLOCK
                        : random_pick < 2 + 3 + 2 ? COBBLESTONE_WALL : STRIPPED_LOG;
                break;
            case SAVANNA: // 9 0 0 1
                signIsOn = random_pick < 9 ? NOTHING : STRIPPED_LOG;
                break;
            case SWAMP: // 6 2 0 2
                signIsOn = random_pick < 6 ? NOTHING : random_pick < 6 + 2 ? COBBLESTONE_BLOCK : STRIPPED_LOG;
                break;
        }

        if (!signIsOn.equals(NOTHING)) {
            signPostYBB++;
            this.placeBlockAtCurrentPosition(
                world,
                signIsOn.equals(COBBLESTONE_BLOCK) ? biomeCobblestoneBlock
                    : signIsOn.equals(COBBLESTONE_WALL) ? biomeCobblestoneWallBlock : biomeStrippedLogVerticBlock,
                signIsOn.equals(COBBLESTONE_BLOCK) ? biomeCobblestoneMeta
                    : signIsOn.equals(COBBLESTONE_WALL) ? biomeCobblestoneWallMeta : biomeStrippedLogVerticMeta,
                2,
                GROUND_LEVEL,
                signPostZBB,
                structureBB);
        }

        // Put foundation underneath the ground
        String underTheGround;
        random_pick = random.nextInt(10);
        int signFoundationDepth = 1;
        switch (this.villageType) {
            default: // NOTHING, COBBLESTONE, STRIPPED LOG
            case PLAINS: // 4 4 2 / 1
                underTheGround = random_pick < 4 ? NOTHING : random_pick < 4 + 4 ? COBBLESTONE_BLOCK : STRIPPED_LOG;
                break;
            case DESERT: // 5 5 0 / 1
            case JUNGLE: // 5 5 0 / 1
                underTheGround = random_pick < 5 ? NOTHING : COBBLESTONE_BLOCK;
                break;
            case TAIGA: // 2 4 4 / 1-2
                underTheGround = random_pick < 2 ? NOTHING : random_pick < 2 + 4 ? COBBLESTONE_BLOCK : STRIPPED_LOG;
                signFoundationDepth = random.nextInt(2) + 1;
                break;
            case SAVANNA: // 10 0 0
                underTheGround = NOTHING;
                break;
            case SNOWY: // 4 2 4 / 1
                underTheGround = random_pick < 4 ? NOTHING : random_pick < 4 + 2 ? COBBLESTONE_BLOCK : STRIPPED_LOG;
                break;
            case SWAMP: // 0 5 5 / 1-2
                underTheGround = random_pick < 5 ? COBBLESTONE_BLOCK : STRIPPED_LOG;
                signFoundationDepth = random.nextInt(2) + 1;
                break;
        }
        if (underTheGround.equals(NOTHING)) {
            signFoundationDepth = 0;
        }

        if (signFoundationDepth != 0) {
            this.fillWithMetadataBlocks(
                world,
                structureBB,
                2,
                GROUND_LEVEL - signFoundationDepth,
                signPostZBB,
                2,
                GROUND_LEVEL - 1,
                signPostZBB,
                underTheGround.equals(COBBLESTONE_BLOCK) ? biomeCobblestoneBlock : biomeStrippedLogVerticBlock,
                underTheGround.equals(COBBLESTONE_BLOCK) ? biomeCobblestoneMeta : biomeStrippedLogVerticMeta,
                underTheGround.equals(COBBLESTONE_BLOCK) ? biomeCobblestoneBlock : biomeStrippedLogVerticBlock,
                underTheGround.equals(COBBLESTONE_BLOCK) ? biomeCobblestoneMeta : biomeStrippedLogVerticMeta,
                false);
        }

        // Surround with fence sometimes
        String enclosingMaterial;
        boolean connectedWalls;
        random_pick = random.nextInt(10);
        switch (this.villageType) {
            default: // NOTHING, FENCE, C. WALL
            case PLAINS: // 3 7 0
                enclosingMaterial = random_pick < 3 ? NOTHING : FENCE;
                connectedWalls = true;
                break;
            case DESERT: // 7 0 3
                enclosingMaterial = random_pick < 7 ? NOTHING : COBBLESTONE_WALL;
                connectedWalls = random.nextBoolean();
                break;
            case TAIGA: // 2 4 4
                enclosingMaterial = random_pick < 2 ? NOTHING : random_pick < 2 + 4 ? FENCE : COBBLESTONE_WALL;
                connectedWalls = random.nextBoolean();
                break;
            case SAVANNA: // 8 2 0
                enclosingMaterial = random_pick < 8 ? NOTHING : FENCE;
                connectedWalls = false;
                break;
            case SNOWY: // 0 5 5
                enclosingMaterial = random_pick < 5 ? FENCE : COBBLESTONE_WALL;
                connectedWalls = true;
                break;
            case JUNGLE: // 7 2 1
                enclosingMaterial = random_pick < 7 ? NOTHING : random_pick < 7 + 2 ? FENCE : COBBLESTONE_WALL;
                connectedWalls = random.nextBoolean();
                break;
            case SWAMP: // 5 5 0
                enclosingMaterial = random_pick < 5 ? NOTHING : FENCE;
                connectedWalls = random.nextInt(10) >= 3;
                break;
        }

        // Build fences
        if (!enclosingMaterial.equals(NOTHING)) {
            for (int[] uuvvww : new int[][] { { 0, GROUND_LEVEL, 3, 4, GROUND_LEVEL, 3 },
                { 0, GROUND_LEVEL, 0, 0, GROUND_LEVEL, 3 }, { 4, GROUND_LEVEL, 0, 4, GROUND_LEVEL, 3 }, }) {
                Block fenceMaterialBlock = enclosingMaterial.equals(FENCE) ? biomeFenceBlock
                    : biomeCobblestoneWallBlock;
                int fenceMaterialMeta = enclosingMaterial.equals(FENCE) ? biomeFenceMeta : biomeCobblestoneWallMeta;

                if (connectedWalls) {
                    this.fillWithMetadataBlocks(
                        world,
                        structureBB,
                        uuvvww[0],
                        uuvvww[1],
                        uuvvww[2],
                        uuvvww[3],
                        uuvvww[4],
                        uuvvww[5],
                        fenceMaterialBlock,
                        fenceMaterialMeta,
                        fenceMaterialBlock,
                        fenceMaterialMeta,
                        false);
                } else {
                    this.placeBlockAtCurrentPosition(
                        world,
                        fenceMaterialBlock,
                        fenceMaterialMeta,
                        uuvvww[0],
                        uuvvww[1],
                        uuvvww[2],
                        structureBB);
                    this.placeBlockAtCurrentPosition(
                        world,
                        fenceMaterialBlock,
                        fenceMaterialMeta,
                        uuvvww[3],
                        uuvvww[4],
                        uuvvww[5],
                        structureBB);
                }

                if (this.villageType == VillageType.SWAMP) {
                    this.func_151554_b(
                        world,
                        biomeStrippedLogVerticBlock,
                        biomeStrippedLogVerticMeta,
                        uuvvww[0],
                        uuvvww[1] - 1,
                        uuvvww[2],
                        structureBB);
                    this.func_151554_b(
                        world,
                        biomeStrippedLogVerticBlock,
                        biomeStrippedLogVerticMeta,
                        uuvvww[3],
                        uuvvww[4] - 1,
                        uuvvww[5],
                        structureBB);
                }
            }
        }

        // Add corner torches sometimes
        boolean includeFrontCornerTorches;
        boolean includeBackCornerTorches;;
        random_pick = random.nextInt(10);
        switch (this.villageType) {
            case SAVANNA:
                includeFrontCornerTorches = random.nextInt(4) < 1;
                includeBackCornerTorches = includeFrontCornerTorches;
                break;
            case JUNGLE:
                includeFrontCornerTorches = random.nextInt(4) < 1;
                includeBackCornerTorches = includeFrontCornerTorches && random.nextBoolean();
                break;
            default:
            case PLAINS:
            case DESERT:
                includeFrontCornerTorches = random.nextInt(4) < 2;
                includeBackCornerTorches = includeFrontCornerTorches && random.nextBoolean();
                break;
            case TAIGA:
            case SNOWY:
            case SWAMP:
                includeFrontCornerTorches = random.nextInt(4) < 3;
                includeBackCornerTorches = includeFrontCornerTorches && random.nextBoolean();
                break;
        }

        // What kind of torch?
        blockObject = ModObjects.chooseModLanternBlock(false);
        Block biomeSittingLanternBlock = (Block) blockObject[0];
        int biomeSittingLanternMeta = (Integer) blockObject[1];

        Block torchlikeBlock = Blocks.torch;
        int torchlikeMeta = 0;
        switch (this.villageType) {
            case SNOWY: // 100% lantern
                torchlikeBlock = biomeSittingLanternBlock;
                torchlikeMeta = biomeSittingLanternMeta;
                break;
            case JUNGLE: // 25% lantern
                if (random.nextInt(4) <= 0) {
                    torchlikeBlock = biomeSittingLanternBlock;
                    torchlikeMeta = biomeSittingLanternMeta;
                }
                break;
            case SWAMP: // 75% lantern
                if (random.nextInt(4) <= 2) {
                    torchlikeBlock = biomeSittingLanternBlock;
                    torchlikeMeta = biomeSittingLanternMeta;
                }
                break;
            default:
                break;
        }

        // Place torches
        int torchHeight = GROUND_LEVEL + (enclosingMaterial.equals(NOTHING) ? 0 : 1);
        if (includeFrontCornerTorches) {
            for (int[] uvw : new int[][] { { 0, torchHeight, 0 }, { 4, torchHeight, 0 }, }) {
                this.placeBlockAtCurrentPosition(
                    world,
                    torchlikeBlock,
                    torchlikeMeta,
                    uvw[0],
                    uvw[1],
                    uvw[2],
                    structureBB);
                // Add support post
                if (this.villageType == VillageType.SWAMP) {
                    this.func_151554_b(
                        world,
                        biomeStrippedLogVerticBlock,
                        biomeStrippedLogVerticMeta,
                        uvw[0],
                        GROUND_LEVEL - 1,
                        uvw[2],
                        structureBB);
                }
            }
        }
        if (includeBackCornerTorches) {
            for (int[] uvw : new int[][] { { 0, torchHeight, 3 }, { 4, torchHeight, 3 }, }) {
                this.placeBlockAtCurrentPosition(
                    world,
                    torchlikeBlock,
                    torchlikeMeta,
                    uvw[0],
                    uvw[1],
                    uvw[2],
                    structureBB);
                // Add support post
                if (this.villageType == VillageType.SWAMP) {
                    this.func_151554_b(
                        world,
                        biomeStrippedLogVerticBlock,
                        biomeStrippedLogVerticMeta,
                        uvw[0],
                        GROUND_LEVEL - 1,
                        uvw[2],
                        structureBB);
                }
            }
        }

        // Signpost itself
        Block signpostsSignBlock = Block.getBlockFromName(ModObjects.signPost_SP);
        if (signpostsSignBlock != null && true) {
            // === Generate AA marker === //
            String markerName = this.nameRoot.trim();
            if (GeneralConfig.antiqueAtlasSignPostLabel.trim()
                .equals("")) {
                if ((this.namePrefix.length() + 1 + this.nameRoot.length()) > 15) {
                    // Prefix+Root is too long
                    if ((nameRoot.length() + 1 + this.nameSuffix.length()) > 15) {
                        // Root+Suffix is too long
                    } else {
                        // Fit Root+Suffix together
                        markerName = (this.nameRoot + " " + this.nameSuffix).trim();
                    }
                } else
                    if ((this.namePrefix.length() + 1 + this.nameRoot.length() + 1 + this.nameSuffix.length()) <= 15) {
                        // Whole name fits on one line!
                        markerName = (this.namePrefix + " " + this.nameRoot + " " + this.nameSuffix).trim();
                    } else {
                        // Only Prefix and Root can fit together
                        markerName = (this.namePrefix + " " + this.nameRoot).trim();
                    }
            } else {
                markerName = GeneralConfig.antiqueAtlasSignPostLabel;
            }

            // Put the sign post into the world
            boolean signPostSuccessfullyMade = true;
            for (int i = 0; i < 2; i++) {
                int signPostMeta = new int[] { StructureVillageVN.getSignRotationMeta(8, this.coordBaseMode, false) / 4,
                    4 }[i];
                boolean isBottom = signPostMeta != 4;
                int signPostYMod = new int[] { 0, 1 }[i];
                // Block
                world.setBlock(
                    signPostX,
                    signPostY + signPostYMod,
                    signPostZ,
                    signpostsSignBlock,
                    signPostMeta, // Meta
                    2);

                // TileEntity
                try {
                    TileEntity tileEntity = world.getTileEntity(signPostX, signPostY, signPostZ);

                    if (tileEntity instanceof harceroi.mc.signposts.block.SignPostTileEntity) {

                        NBTTagCompound signPostTagCompound = new NBTTagCompound();
                        tileEntity.writeToNBT(signPostTagCompound);
                        if (isBottom) {

                            // String label = this.nameRoot.trim() + " WP";
                            double jumpX = signPostX + (signPostMeta == 1 ? -1 : signPostMeta == 3 ? 1 : 0) + 0.5;
                            double jumpY = signPostY;
                            double jumpZ = signPostZ + (signPostMeta == 0 ? 1 : signPostMeta == 2 ? -1 : 0) + 0.5;

                            // Rewrite of SignPostsMod.addJumpTarget() to not require an EntityPlayer
                            harceroi.mc.signposts.block.SignPostTileEntity signPost = (harceroi.mc.signposts.block.SignPostTileEntity) tileEntity;
                            int markerId = harceroi.mc.signposts.SignPostsMod
                                .addGlobalMarker(signX, signZ, markerName, world);
                            signPost.setAsJumpLocation(jumpX, jumpY, jumpZ, markerId);
                            harceroi.mc.signposts.data.MarkerToTileMap.get(world)
                                .setTileForMarker(signX, signY, signZ, markerId);

                            signPostTagCompound.setDouble(
                                "jumpX",
                                signPostX + (signPostMeta == 1 ? -1 : signPostMeta == 3 ? 1 : 0) + 0.5);
                            signPostTagCompound.setDouble("jumpY", signPostY);
                            signPostTagCompound.setDouble(
                                "jumpZ",
                                signPostZ + (signPostMeta == 0 ? 1 : signPostMeta == 2 ? -1 : 0) + 0.5);
                            signPostTagCompound.setInteger("markerId", markerId);

                            tileEntity.readFromNBT(signPostTagCompound);
                            tileEntity.markDirty();
                        }

                    }
                } catch (Exception e) {
                    signPostSuccessfullyMade = false;
                    LogHelper.info(
                        "Error while assigning TileEntity to SignPost at " + signPostX
                            + " "
                            + (signPostY + signPostYMod)
                            + " "
                            + signPostZ
                            + ":");
                    LogHelper.info(e);
                }
            }
        }

        // Clean items
        if (VillageGeneratorConfigHandler.cleanDroppedItems) {
            StructureVillageVN.cleanEntityItems(world, this.boundingBox);
        }

        return true;
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    @Override
    protected int getVillagerType(int number) {
        return 0;
    }
}
