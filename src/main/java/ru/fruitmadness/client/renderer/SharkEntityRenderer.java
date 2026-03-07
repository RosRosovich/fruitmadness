package ru.fruitmadness.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.client.model.BabySharkEntityModel;
import ru.fruitmadness.client.model.SharkEntityModel;
import ru.fruitmadness.entity.SharkEntity;

public class SharkEntityRenderer extends MobEntityRenderer<SharkEntity, EntityModel<SharkEntity>> {

    private static final Identifier BIG_TEXTURE =
            Identifier.of(FruitMadness.MOD_ID, "textures/entity/shark_big.png");

    private static final Identifier BABY_TEXTURE =
            Identifier.of(FruitMadness.MOD_ID, "textures/entity/shark_baby.png");

    private final SharkEntityModel bigModel;
    private final BabySharkEntityModel babyModel;

    public SharkEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SharkEntityModel(context.getPart(SharkEntityModel.SHARK_LAYER)), 0.5f);

        this.bigModel = new SharkEntityModel(context.getPart(SharkEntityModel.SHARK_LAYER));
        this.babyModel = new BabySharkEntityModel(context.getPart(BabySharkEntityModel.BABY_SHARK_LAYER));
    }

    @Override
    public Identifier getTexture(SharkEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE : BIG_TEXTURE;
    }

    @Override
    public void render(SharkEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider buffer, int light) {

        this.model = entity.isBaby() ? babyModel : bigModel;

        super.render(entity, yaw, tickDelta, matrices, buffer, light);
    }
}