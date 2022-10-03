public class CombatController {
    private Player player;

    public CombatController(Player player) {
        this.player = player;
    }



    public int attackPlayer() {
        player.setHealth(player.getHealth() - player.currentEnemy().getDamage());
        return player.currentEnemy().getDamage();
    }

    public int attackEnemy() {
        player.currentEnemy().setHealth(player.currentEnemy().getHealth() - player.getEquippedWeapon().getDamage());
        return player.getEquippedWeapon().getDamage();
    }



}
