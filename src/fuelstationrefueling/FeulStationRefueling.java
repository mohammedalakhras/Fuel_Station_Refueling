package fuelstationrefueling;

import eduni.simjava.Sim_system;
import static eduni.simjava.Sim_system.TIME_ELAPSED;

public class FeulStationRefueling {

    public static FuelPump fuelPump1;
    public static FuelPump fuelPump2;
    public static FuelPump fuelPump3;
    public static int localfueltank = 3000;
    public static boolean b = true;

    public static void main(String[] args) {
        Sim_system.initialise();
        Source source = new Source("source", 15);
        fuelPump1 = new FuelPump("fuelPump1", 4, 2);
        fuelPump2 = new FuelPump("fuelPump2", 6.3, 3.1);
        fuelPump3 = new FuelPump("fuelPump3", 7.8, 2.5);
        FuelTank fuelTank = new FuelTank("fuelTank");
        Sim_system.link_ports("source", "out1", "fuelPump1", "in");
        Sim_system.link_ports("source", "out2", "fuelPump2", "in");
        Sim_system.link_ports("source", "out3", "fuelPump3", "in");
        Sim_system.link_ports("fuelPump1", "out", "fuelTank", "in1");
        Sim_system.link_ports("fuelPump2", "out", "fuelTank", "in2");
        Sim_system.link_ports("fuelPump3", "out", "fuelTank", "in3");
        
         Sim_system.set_trace_detail(false, true, true);
         Sim_system.set_transient_condition(TIME_ELAPSED,40320);
        Sim_system.run();
    }
}
