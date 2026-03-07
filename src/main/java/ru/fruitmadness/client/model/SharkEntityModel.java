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

public class SharkEntityModel extends EntityModel<SharkEntity> {

    public static final EntityModelLayer SHARK_LAYER = new EntityModelLayer(
            Identifier.of(FruitMadness.MOD_ID, "shark"), "main");

    private final ModelPart Shark;
    private final ModelPart FinL;
    private final ModelPart FinR;
    private final ModelPart Tail;
    private final ModelPart bone8;
    private final ModelPart Body;
    private final ModelPart Head;
    private final ModelPart Jaw;

    public SharkEntityModel(ModelPart root) {
        this.Shark = root.getChild("Shark");
        this.FinL = this.Shark.getChild("FinL");
        this.FinR = this.Shark.getChild("FinR");
        this.Tail = this.Shark.getChild("Tail");
        this.bone8 = this.Tail.getChild("bone8");
        this.Body = this.Shark.getChild("Body");
        this.Head = this.Body.getChild("Head");
        this.Jaw = this.Head.getChild("Jaw");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData Shark = root.addChild("Shark",
                ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData FinL = Shark.addChild("FinL",
                ModelPartBuilder.create()
                        .uv(74, 80).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 1.0F, 4.0F)
                        .uv(74, 73).cuboid(4.0F, 0.0F, -2.0F, 12.0F, 1.0F, 6.0F),
                ModelTransform.pivot(4.0F, -1.0F, -3.0F));

        ModelPartData FinR = Shark.addChild("FinR",
                ModelPartBuilder.create()
                        .uv(74, 66).cuboid(-16.0F, 0.0F, -2.0F, 12.0F, 1.0F, 6.0F)
                        .uv(74, 85).cuboid(-4.0F, 0.0F, -2.0F, 4.0F, 1.0F, 4.0F),
                ModelTransform.pivot(-4.0F, -1.0F, -3.0F));

        ModelPartData Tail = Shark.addChild("Tail",
                ModelPartBuilder.create()
                        .uv(0, 45).cuboid(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 16.0F),
                ModelTransform.pivot(0.0F, -10.0F, 24.0F));

        ModelPartData bone8 = Tail.addChild("bone8",
                ModelPartBuilder.create()
                        .uv(44, 66).cuboid(-1.0F, -3.0716F, -2.2981F, 2.0F, 7.0F, 13.0F)
                        .uv(80, 10).cuboid(-1.0F, -17.0716F, -2.2981F, 2.0F, 14.0F, 5.0F),
                ModelTransform.pivot(0.0F, 0.0F, 16.0F));

        ModelPartData Body = Shark.addChild("Body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4.0F, -7.0F, -15.0F, 8.0F, 13.0F, 32.0F)
                        .uv(26, 80).cuboid(-1.0F, -10.0F, 1.0F, 2.0F, 3.0F, 7.0F)
                        .uv(44, 86).cuboid(-1.0F, -14.0F, 1.0F, 2.0F, 4.0F, 4.0F),
                ModelTransform.pivot(0.0F, -6.0F, 7.0F));

        ModelPartData Head = Body.addChild("Head",
                ModelPartBuilder.create()
                        .uv(44, 45).cuboid(-3.0F, -4.0F, -16.0F, 6.0F, 5.0F, 16.0F)
                        .uv(0, 80).cuboid(-2.0F, 1.0F, -10.9F, 4.0F, 1.0F, 9.0F),
                ModelTransform.pivot(0.0F, -3.0F, -15.0F));

        Head.addChild("Jaw",
                ModelPartBuilder.create()
                        .uv(0, 67).cuboid(-3.0F, -2.0F, -11.0F, 6.0F, 2.0F, 11.0F)
                        .uv(80, 0).cuboid(-2.0F, -3.0F, -10.9F, 4.0F, 1.0F, 9.0F)
                        .uv(80, 39).cuboid(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 3.0F),
                ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    @Override
    public void setAngles(SharkEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        float speed = 0.5f;
        float tailWag = MathHelper.sin(ageInTicks * speed) * 0.3f;
        this.Tail.yaw = tailWag;
        this.bone8.yaw = tailWag * 0.5f;

        if (entity.isAttacking()) {
            this.Jaw.pitch = 0.5f;
        } else {
            this.Jaw.pitch = 0.0f;
        }

        this.Head.yaw = netHeadYaw * MathHelper.RADIANS_PER_DEGREE * 0.3f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        Shark.render(matrices, vertices, light, overlay, color);
    }
}