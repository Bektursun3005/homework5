import java.util.Arrays;
import java.util.Random;

public class Main {
    public static int bossHealth = 400;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttack = {"Physical", "Magical", "Kinetic", "Medic"};
    public static int[] heroesHealth = {200, 270, 250, 300};
    public static int[] heroesDamage = {10, 15, 20, 0};
    public static int roundNumber;

    public static void main(String[] args) {
        System.out.println("-----------Игра-----------");
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + "--------------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " deffence: " + (bossDefence == null ? "No deffence" : bossDefence));
        // String defence;
        // if (bossDefence == null) {
        // 	defence = "No defence"
        // } else {
        // 	defence = bossDefence
        // }
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttack[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }


    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won");
            return true;
        }
//        if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
//            System.out.println("Boss won");
//            return true;
//        }
//        return false;
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won");
        }
        return allHeroesDead;
    }

    public static void bossAttack() {
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

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttack[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void treatment() {
        int min = heroesHealth[0]; // Предполагаем, что первый элемент массива - минимальный
        int minIndex = 0;
        // Проходите по массиву, чтобы найти минимальное значение и его индекс
        for (int i = 1; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < min) {
                min = heroesHealth[i];
                minIndex = i;
            }
        }
        if (heroesHealth[minIndex] < 100 && heroesHealth[minIndex] > 0 && heroesHealth[3] > 0) {
            Random random = new Random();
            int health = random.nextInt(35, 50);
            heroesHealth[minIndex] = heroesHealth[minIndex] + health;
        }
    }

    public static void chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttack.length);
        bossDefence = heroesAttack[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseDefence();
        bossAttack();
        heroesAttack();
        treatment();
        showStatistics();
    }

}