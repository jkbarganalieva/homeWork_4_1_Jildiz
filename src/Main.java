import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefenseType;
    public static int[] heroesHealth = {260, 270, 280, 290, 400, 250, 240,230};
    public static int[] heroesDamage = {25, 20, 15, 0, 5, 20, 10,15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk","Thor"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefense();
        medicHealsHeroes();
        bossHit();
        heroesHit();
        evasionLucky();
        takesHitGolem();
        blockBerserk();
        thor();
        printStatistics();
        chooseBossDefense();
    }

    public static void printStatistics() {
        System.out.println("ROUND: " + roundNumber + " --------------");
        System.out.println("BOSS Health: " + bossHealth + "; damage: "
                + bossDamage + "; defense: " + bossDefenseType);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + "  Health: " + heroesHealth[i] + "; damage: "
                    + heroesDamage[i]);
        }

    }

    public static void chooseBossDefense() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);//0,1,2
        bossDefenseType = heroesAttackType[randomIndex];
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("heroes won!");
            return true;
        }

        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("boss won!");
            return true;
        }
        return false;*/

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("boss won!");
        }

        return allHeroesDead;
    }

    public static void bossHit() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }

            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {

                int hit = heroesDamage[i];
                if (heroesAttackType[i] == bossDefenseType) {

                    Random random = new Random();
                    int coeff = random.nextInt(6) + 2;
                    hit = heroesDamage[i] * coeff;
                    System.out.println("crit damage: " + hit);
                }
                if (bossHealth - hit < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - hit;
                }
            }

        }
    }

    // основное дз4
    public static void medicHealsHeroes() {
        for (int d = 0; d < heroesDamage.length; d++) {
            if (d == 3) {
                continue;
            }
            if (heroesHealth[d] > 0 && heroesHealth[d] < 100 && heroesHealth[3] > 0) {
                Random random = new Random();
                int a = 25; // Начальное значение диапазона - "от"
                int b = 40; // Конечное значение диапазона - "до"

                int increaseHealth = a + (int) (Math.random() * b);
                int randomIndex = random.nextInt(heroesHealth.length);

                heroesHealth[d] = heroesHealth[d] + increaseHealth;
                System.out.println("увеличен. здоровье: "+increaseHealth);


                //System.out.println("medic heal heroes: " + heroesAttackType[randomIndex]);
                System.out.println("medic heal heroes: " + heroesAttackType[d]);
                break;
            }
           /* System.out.println("medic heal heroes: " + heroesAttackType[d]);
            break;*/

        }
    }

    //дз4 на сообразительность
    public static void takesHitGolem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 4) {
                continue;
            }
            if (heroesHealth[i] > 0) {
                heroesHealth[i] = heroesHealth[i] + bossDamage / 5;
            }
            //heroesHealth[4]=heroesHealth[4]-bossDamage/5;
            if (heroesHealth[4] - bossDamage / 5 < 0) {
                heroesHealth[4] = 0;
            } else {
                heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
            }
            System.out.println("часть удара: " + bossDamage / 5);
            break;

        }
    }

    public static void evasionLucky() {
        Random random = new Random();
        boolean evasion = random.nextBoolean();
        if (heroesHealth[5] > 0 && evasion == true) {

            heroesHealth[5] = heroesHealth[5] + bossDamage;
            System.out.println(" Lucky  evasion: ");
        } else
            System.out.println("Lucky not evasion: ");

    }

    public static void blockBerserk() {
        Random random = new Random();
        int a = 5; // Начальное значение диапазона - "от"
        int b = 20; // Конечное значение диапазона - "до"
        int blockDamage = a + (int) (Math.random() * b);
        if (heroesHealth[6] > 0 && bossHealth > 0){
            heroesHealth[6] = heroesHealth[6] + bossDamage-blockDamage;
            bossHealth=bossHealth-heroesDamage[6]+blockDamage;
            System.out.println("Блокированный удар: "+blockDamage);
        }

    }

    public static void thor () {
        Random random = new Random();
        boolean Thor = random.nextBoolean();
        if (Thor) {
            bossDamage = 0;
            System.out.println("Boss  в этом раунде накаутирован");
        } else {
            bossDamage = 50;
        }
    }

}

