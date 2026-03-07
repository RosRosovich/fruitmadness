package ru.fruitmadness.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.entity.SharkEntity;

public class BabySharkEntityModel extends EntityModel<SharkEntity> {

    public static final EntityModelLayer BABY_SHARK_LAYER = new EntityModelLayer(
            Identifier.of(FruitMadness.MOD_ID, "baby_shark"), "main");

    private final ModelPart Body;
    private final ModelPart Tail;
    private final ModelPart Head;
    private final ModelPart FinL;
    private final ModelPart FinR;

    public BabySharkEntityModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Tail = this.Body.getChild("Tail");
        this.Head = this.Body.getChild("Head");
        this.FinL = this.Body.getChild("FinL");
        this.FinR = this.Body.getChild("FinR");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData Body = root.addChild("Body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2.0F, -3.0F, -6.0F, 4.0F, 7.0F, 12.0F)
                        .uv(32, 8).cuboid(-1.0F, -5.0F, -2.0F, 2.0F, 2.0F, 4.0F)
                        .uv(34, 23).cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 2.0F, 2.0F),
                ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        ModelPartData Tail = Body.addChild("Tail",
                ModelPartBuilder.create()
                        .uv(18, 19).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 3.0F, 6.0F),
                ModelTransform.pivot(0.0F, -2.0F, 6.0F));

        Tail.addChild("bone2_r1",
                ModelPartBuilder.create()
                        .uv(34, 14).cuboid(-1.0F, -6.0F, -2.0F, 1.0F, 6.0F, 3.0F)
                        .uv(0, 29).cuboid(-1.0F, 0.0F, -2.0F, 1.0F, 3.0F, 6.0F),
                ModelTransform.pivot(0.5F, 0.0F, 7.5F));

        Body.addChild("Head",
                ModelPartBuilder.create()
                        .uv(0, 19).cuboid(-2.0F, -3.0F, -5.0F, 4.0F, 5.0F, 5.0F)
                        .uv(18, 28).cuboid(-2.0F, -3.0F, -9.0F, 4.0F, 4.0F, 4.0F),
                ModelTransform.pivot(0.0F, 0.0F, -6.0F));

        Body.addChild("FinL",
                ModelPartBuilder.create()
                        .uv(34, 27).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F)
                        .uv(32, 4).cuboid(2.0F, 0.0F, -2.0F, 6.0F, 1.0F, 3.0F),
                ModelTransform.pivot(2.0F, 3.0F, -4.0F));

        Body.addChild("FinR",
                ModelPartBuilder.create()
                        .uv(32, 0).cuboid(-8.0F, 0.0F, -2.0F, 6.0F, 1.0F, 3.0F)
                        .uv(34, 30).cuboid(-2.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F),
                ModelTransform.pivot(-2.0F, 3.0F, -4.0F));

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(SharkEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float tailWag = MathHelper.sin(ageInTicks * 1.0f) * 0.5f;
        this.Tail.yaw = tailWag;
        this.Head.yaw = netHeadYaw * MathHelper.RADIANS_PER_DEGREE * 0.2f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        Body.render(matrices, vertices, light, overlay, color);
    }
}