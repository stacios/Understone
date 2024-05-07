package Model;

public interface Collidable {
    int [] getHitbox(); //[x,y,width,height]

    default boolean colliding(Collidable other){
        int[] hitbox1 = this.getHitbox();
        int[] hitbox2 = other.getHitbox();

        int centerX1 = hitbox1[0];
        int centerY1 = hitbox1[1];
        int w1 = hitbox1[2];
        int h1 = hitbox1[3];

        int centerX2 = hitbox2[0];
        int centerY2 = hitbox2[1];
        int w2 = hitbox2[2];
        int h2 = hitbox2[3];

        int x1 = centerX1 - w1/2;
        int y1 = centerY1 - h1/2;
        int x2 = centerX2 - w2/2;
        int y2 = centerY2 - h2/2;

        return  x1 < x2 + w2 &&
                x2 < x1 + w1 &&
                y1 < y2 + h2 &&
                y2 < y1 + h1;
    }
}
