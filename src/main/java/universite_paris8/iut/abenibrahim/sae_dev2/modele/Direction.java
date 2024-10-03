package universite_paris8.iut.abenibrahim.sae_dev2.modele;

public enum Direction
{
    NORD(0,-1),
    SUD(0,1),
    OUEST(-1,0),
    EST(1,0);

    private int x,y;
    Direction(int x,int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }
}
