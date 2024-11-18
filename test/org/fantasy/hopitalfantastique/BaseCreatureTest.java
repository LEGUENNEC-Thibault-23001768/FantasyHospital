package org.fantasy.hopitalfantastique;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

public class BaseCreatureTest {

    private BaseCreature baseCreature;
    private Elfe elfe;
    private Orque orque;
    private Nain nain;
    private Vampire vampire;

    static final int MORAL_BASE = 10;
    @BeforeEach
    public void setUp() {
        elfe = new Elfe("Legolas", SEXE.HOMME, 70, 180, 2931, MORAL_BASE);
        orque = new Orque("Gorbag", SEXE.HOMME, 100, 190, 60, MORAL_BASE);
        nain = new Nain("Gimli", SEXE.HOMME, 80, 140, 139, MORAL_BASE);
        vampire = new Vampire("Dracula", SEXE.HOMME, 75, 185, 500, MORAL_BASE);
        baseCreature = new BaseCreature("Wangwang", SEXE.HOMME, 75, 175, 25, MORAL_BASE) {
            public void demoraliser() {}
            public void contaminer() {}
            public void regenerer() {}
        };
    }

    @Test
    public void testAttendreReduitMoral() {
        elfe.attendre();
        assertEquals(MORAL_BASE-2, elfe.getMoral());

        orque.attendre();
        assertEquals(MORAL_BASE-1, orque.getMoral());
    }

    @Test
    public void testTomberMalade() {
        assertTrue(baseCreature.getMaladies().isEmpty());
        assertEquals(MORAL_BASE, baseCreature.getMoral());

        baseCreature.tomber_malade("BG");
        List<Maladie> maladieList = baseCreature.getMaladies();
        assertEquals(1, maladieList.size());
        assertEquals(MORAL_BASE-2, baseCreature.getMoral());
    }

    @Test
    public void testTomberMaladieInconnu() {
        assertTrue(baseCreature.getMaladies().isEmpty());
        baseCreature.tomber_malade("AAAAAABBBBBBCCCCC");
        assertTrue(baseCreature.getMaladies().isEmpty());
    }

    @Test
    public void testSoignerMaladie() {
        baseCreature.tomber_malade("BG");
        baseCreature.tomber_malade("MDC");

        assertEquals(2, baseCreature.getMaladies().size());
        assertEquals(6, baseCreature.getMoral());

        baseCreature.soigner();

        assertEquals(1,baseCreature.getMaladies().size());
        assertEquals(8, baseCreature.getMoral());
        assertEquals("MDC", baseCreature.getMaladies().get(0).getNomCourt());

        baseCreature.soigner();

        assertEquals(0,baseCreature.getMaladies().size());
        assertEquals(10, baseCreature.getMoral());
    }


}
