package ru.fruitmadness.client.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.client.model.BabySharkEntityModel;
import ru.fruitmadness.entity.SharkEntity;

public class BabySharkRenderer extends MobEntityRenderer<SharkEntity, BabySharkEntityModel> {

    private static final Identifier TEXTURE = Identifier.of(FruitMadness.MOD_ID, "textures/entity/shark_baby.png");

    public BabySharkRenderer(EntityRendererFactory.Context context) {
        super(context, new BabySharkEntityModel(context.getPart(BabySharkEntityModel.BABY_SHARK_LAYER)), 0.3f);
    }

    @Override
    public Identifier getTexture(SharkEntity entity) {
        return TEXTURE;
    }
}