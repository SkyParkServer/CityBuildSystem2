package de.skypark.citybuild;

import de.skypark.citybuild.commands.*;
import de.skypark.citybuild.core.CityBuildSettings;
import de.skypark.citybuild.core.HomeConfig;
import de.skypark.citybuild.core.HomeService;
import de.skypark.citybuild.core.MessageManager;
import de.skypark.citybuild.listeners.HomeGuiListener;
import de.skypark.citybuild.listeners.HomeJoinListener;
import de.skypark.citybuild.listeners.PlayerDataListener;
import de.skypark.citybuild.listeners.SharedEnderChestListener;
import de.skypark.citybuild.listeners.MessagingJoinListener;
import de.skypark.citybuild.listeners.InvseeReadOnlyListener;
import de.skypark.citybuild.listeners.TablistJoinListener;
import de.skypark.citybuild.listeners.LuckPermsUpdateListener;
import de.skypark.citybuild.listeners.GlobalSpawnListener;
import de.skypark.citybuild.listeners.RainbowArmorListener;
import de.skypark.citybuild.storage.*;
import net.luckperms.api.messaging.MessagingService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.UUID;

public class CityBuildSystem extends JavaPlugin {

    private static CityBuildSystem instance;

    private CityBuildSettings settings;
    private MessageManager messages;
    private DataManager data;
    private GlobalState globals;
    private MoneyStore money;
    private CooldownStore cooldowns;
    private PlayerDataStore playerData;

    private EnderChestStore enderChestStore;
    private WarpStore warpStore;

    private MessagingStore messagingStore;
    private PlayerLookupStore playerLookup;
    private MessagingService messaging;

    private CrystalsStore crystals;
    private BankStore bank;
    private WerbungStore werbungStore;


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

        this.enderChestStore = new EnderChestStore(data);
        this.warpStore = new WarpStore(data);

        this.messagingStore = new MessagingStore(data);
        this.playerLookup = new PlayerLookupStore(data);


        this.crystals = new CrystalsStore(data);
        this.bank = new BankStore(data);
        this.werbungStore = new WerbungStore(data);

        this.homeConfig = new HomeConfig(this);
        this.homes = new HomeService(this, homeConfig);

        // Events
        getServer().getPluginManager().registerEvents(new PlayerDataListener(this, playerData), this);
        getServer().getPluginManager().registerEvents(new SharedEnderChestListener(this), this);
        getServer().getPluginManager().registerEvents(new HomeJoinListener(), this);
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

        // Commands - extras
        getCommand("enderchest").setExecutor(new EnderChestCommand(this));
        getCommand("ec").setExecutor(new EnderChestCommand(this));
        getCommand("speed").setExecutor(new SpeedCommand(this));
        getCommand("hat").setExecutor(new HatCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("delwarp").setExecutor(new DelWarpCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("warps").setExecutor(new WarpsCommand(this));

        // Commands - messaging
        MsgCommand msgCmd = new MsgCommand(this);
        getCommand("msg").setExecutor(msgCmd);
        getCommand("msg").setTabCompleter(msgCmd);
        getCommand("r").setExecutor(new ReplyCommand(this));
        getCommand("msgtoggle").setExecutor(new MsgToggleCommand(this));

        // Commands - admin utils
        InvseeCommand invsee = new InvseeCommand(this);
        getCommand("invsee").setExecutor(invsee);
        getCommand("invsee").setTabCompleter(invsee);
        getCommand("near").setExecutor(new NearCommand(this));

        FeedCommand feed = new FeedCommand(this);
        getCommand("feed").setExecutor(feed);
        getCommand("feed").setTabCompleter(feed);

        HealCommand heal = new HealCommand(this);
        getCommand("heal").setExecutor(heal);
        getCommand("heal").setTabCompleter(heal);

        getCommand("kristalle").setExecutor(new KristalleCommand(this, crystals));

        getCommand("bank").setExecutor(new BankCommand(this, bank));
        getCommand("bank").setTabCompleter(new BankCommand(this, bank));

        getCommand("werbung").setExecutor(new WerbungCommand(this, werbungStore));
        getCommand("werbungtp").setExecutor(new WerbungTpCommand(this, werbungStore));
        getCommand("regenbogen").setExecutor(new RegenbogenCommand(this));

        // Commands - admin
        getCommand("cb").setExecutor(new CbCommand(this));
        getCommand("cb").setTabCompleter(new CbTabCompleter());
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

    public EnderChestStore enderChestStore() { return enderChestStore; }

    public MessagingService messaging() { return messaging; }

    public CrystalsStore crystals() { return crystals; }
    public BankStore bank() { return bank; }
    public WerbungStore werbungStore() { return werbungStore; }

}
