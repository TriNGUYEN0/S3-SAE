package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;

import static org.junit.jupiter.api.Assertions.*;
public class ProjectileTest {
    private Projectile projectile;

    @BeforeEach
    void setUp() {
        projectile = new Projectile(0, 0, Direction.EST, 5, 10);
    }

    @Test
    void testConstructeur() {
        assertEquals(0, projectile.getX());
        assertEquals(0, projectile.getY());
        assertEquals(Direction.EST, projectile.getDirection());
        assertEquals(5, projectile.getVitesse());
        assertEquals(10, projectile.getDegat());
    }

    @Test
    void testSettersEtGetters() {
        projectile.setX(10);
        projectile.setY(20);
        assertEquals(10, projectile.getX());
        assertEquals(20, projectile.getY());
        assertEquals(10, projectile.xProperty().get());
        assertEquals(20, projectile.yProperty().get());
    }

    @Test
    void testDeplacerNord() {
        projectile.setDirection(Direction.NORD);
        int yBefore = projectile.getY();
        projectile.deplacer();
        assertEquals(yBefore - projectile.getVitesse(), projectile.getY());
    }

    @Test
    void testDeplacerSud() {
        projectile.setDirection(Direction.SUD);
        int yBefore = projectile.getY();
        projectile.deplacer();
        assertEquals(yBefore + projectile.getVitesse(), projectile.getY());
    }

    @Test
    void testDeplacerOuest() {
        projectile.setDirection(Direction.OUEST);
        int xBefore = projectile.getX();
        projectile.deplacer();
        assertEquals(xBefore - projectile.getVitesse(), projectile.getX());
    }

    @Test
    void testDeplacerEst() {
        projectile.setDirection(Direction.EST);
        int xBefore = projectile.getX();
        projectile.deplacer();
        assertEquals(xBefore + projectile.getVitesse(), projectile.getX());
    }
}
