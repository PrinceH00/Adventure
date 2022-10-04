public class CombatController {
    private Player player;

    public CombatController(Player player) {
        this.player = player;
    }



    public double attackPlayer() {
        player.setHealth((player.getHealth() - player.currentEnemy().getDamage())*
                1-(player.getEquippedArmor().getArmorClass()/10));
        return player.currentEnemy().getDamage();
    }

    public double attackEnemy() {
        player.currentEnemy().setHealth(player.currentEnemy().getHealth() - player.getEquippedWeapon().getDamage());
        return player.getEquippedWeapon().getDamage();
    }

    public String attack() {
        StringBuilder stringBuilder = new StringBuilder();
        if (player.getCurrentRoom().getHasEnemy() && player.getEquippedWeapon() != null) {
            attackEnemy();
            stringBuilder.append(String.format("You have attacked %s and dealt %s damage. \n",
                    player.getCurrentRoom().getEnemy().getName(),
                    player.getEquippedWeapon().getDamage()));
            attackPlayer();
            stringBuilder.append(String.format("You have been attacked and have taken %s damage. \n",
                    player.getCurrentRoom().getEnemy().getDamage()));
            stringBuilder.append(String.format("Player: %s/%s health\n", player.getHealth(), player.getMAX_HEALTH()));
            stringBuilder.append(String.format("%s: %s/%s health\n", player.getCurrentRoom().getEnemy().getName(),
                    player.getCurrentRoom().getEnemy().getHealth(),
                    player.getCurrentRoom().getEnemy().getMAX_HEALTH()));
        } else if (!player.getCurrentRoom().getHasEnemy()) {
            stringBuilder.append("There is no enemy enemy in this room.");
        } else {
            stringBuilder.append("You have no weapon equipped.");
        }
        return stringBuilder.toString();
    }

}
