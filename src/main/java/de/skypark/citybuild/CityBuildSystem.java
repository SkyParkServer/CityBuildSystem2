package de.skypark.citybuild;

import de.skypark.citybuild.commands.*;
import de.skypark.citybuild.core.CityBuildSettings;
import de.skypark.citybuild.core.HomeConfig;
import de.skypark.citybuild.core.HomeService;
import de.skypark.citybuild.core.MessageManager;
import de.skypark.citybuild.listeners.HomeGuiListener;
import de.skypark.citybuild.listeners.HomeJoinListener;
import de.skypark.citybuild.listeners.PlayerDataListener;
import de.skypark.citybuild.storage.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class CityBuildSystem extends JavaPlugin {

    private static CityBuildSystem instance;

    private CityBuildSettings settings;
    private MessageManager messages;
    private DataManager data;
    private GlobalState globals;
    private MoneyStore money;
    private CooldownStore cooldowns;
    private PlayerDataStore playerData;

    private HomeConfig homeConfig;
    private HomeService homes;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.settings = new CityBuildSettings(this);
        this.data = new DataManager(this);
        this.globals = new GlobalState(data, settings);
        this.messages = new MessageManager(this);
        this.money = new MoneyStore(data);
        this.cooldowns = new CooldownStore(data);
        this.playerData = new PlayerDataStore(data, settings);

        this.homeConfig = new HomeConfig(this);
        this.homes = new HomeService(this, homeConfig);

        // Events
        getServer().getPluginManager().registerEvents(new PlayerDataListener(this, playerData), this);
        getServer().getPluginManager().registerEvents(new HomeJoinListener(homes), this);
        getServer().getPluginManager().registerEvents(new HomeGuiListener(this, homes), this);

        // Commands - spawn
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));

        // Commands - homes
        getCommand("home").setExecutor(new HomeCommand(this, homes));
        getCommand("homes").setExecutor(new HomesCommand(this, homes));
        getCommand("sethome").setExecutor(new SetHomeCommand(this, homes));
        getCommand("delhome").setExecutor(new DelHomeCommand(this, homes));

        // Commands - economy
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("eco").setExecutor(new EcoCommand(this));
        getCommand("eco").setTabCompleter(new EcoTabCompleter());

        // Commands - QoL
        getCommand("workbench").setExecutor(new WorkbenchCommand(this));
        getCommand("anvil").setExecutor(new AnvilCommand(this));
        getCommand("sign").setExecutor(new SignCommand(this));
        getCommand("repair").setExecutor(new RepairCommand(this));
        getCommand("wetter").setExecutor(new WetterCommand(this));
        getCommand("wetter").setTabCompleter(new WetterTabCompleter());

        // Commands - admin
        getCommand("cb").setExecutor(new CbCommand(this));
        getCommand("cb").setTabCompleter(new CbTabCompleter());

        getLogger().info("CityBuildSystem enabled.");
    }

    @Override
    public void onDisable() {
        data.saveAll();
    }

    public static CityBuildSystem getInstance() {
        return instance;
    }

    public CityBuildSettings settings() { return settings; }
    public MessageManager messages() { return messages; }
    public DataManager data() { return data; }
    public GlobalState globals() { return globals; }
    public MoneyStore money() { return money; }
    public CooldownStore cooldowns() { return cooldowns; }
    public PlayerDataStore playerData() { return playerData; }

    public HomeConfig homeConfig() { return homeConfig; }
    public HomeService homes() { return homes; }
}
