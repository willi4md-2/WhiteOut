package net.willi4md.whiteout.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
public class StoreCoordinates {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File coordsFile = new File("config/coords.json");

        dispatcher.register(
                Commands.literal("savecoords")
                        .then(Commands.argument("name", StringArgumentType.word()))
                            .executes(context -> {
                                ServerPlayer player = context.getSource().getPlayerOrException();
                                BlockPos pos = player.blockPosition();
                                String name = StringArgumentType.getString(context, "name");
                                Coords coords = new Coords(pos.getX(), pos.getY(), pos.getZ());
                                NamedCoords namedCoords = new NamedCoords(name, coords);
                                String json = gson.toJson(coords);
                                try {
                                    FileWriter writer = new FileWriter(coordsFile);
                                    writer.write(json);
                                    writer.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                player.displayClientMessage(Component.literal("Coordinates Saved!"), true);
                                player.teleportTo(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
                                return 1;
                            })
        );
    }

}