package ru.fruitmadness.client.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.entity.GoldenPitEntity;

public class GoldenPitEntityRenderer extends EntityRenderer<GoldenPitEntity> {

    private final ItemRenderer itemRenderer;
    private static final Identifier TEXTURE = Identifier.of(FruitMadness.MOD_ID, "textures/item/golden_pit.png");

    public GoldenPitEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15f;
    }

    @Override
    public void render(GoldenPitEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // Вращение в полете
        if (!entity.isOnGround()) {
            float rotation = (entity.getWorld().getTime() + tickDelta) * 50 % 360;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotation * 0.7f));
        }

        // Масштаб
        matrices.scale(0.5f, 0.5f, 0.5f);

        // Рендеринг предмета
        this.itemRenderer.renderItem(
                entity.getStack(),
                ModelTransformationMode.GROUND,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                entity.getId()
        );

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(GoldenPitEntity entity) {
        return TEXTURE;
    }
}