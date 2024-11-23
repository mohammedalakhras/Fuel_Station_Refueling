package fuelstationrefueling;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_stat;
import eduni.simjava.Sim_system;
import static fuelstationrefueling.FeulStationRefueling.*;

public class FuelTank extends Sim_entity {

    Sim_port in1, in2, in3;
    double delay = 300;
    public static int count=0;
    Sim_stat stat;
    public FuelTank(String name) {
        super(name);
        stat = new Sim_stat();
        in1 = new Sim_port("in1");
        add_port(in1);
        in2 = new Sim_port("in2");
        add_port(in2);
        in3 = new Sim_port("in3");
        add_port(in3);
        stat.add_measure(Sim_stat.THROUGHPUT);
        stat.add_measure(Sim_stat.QUEUE_LENGTH);
        stat.add_measure(Sim_stat.UTILISATION);
        set_stat(stat);
    }

    @Override
    public void body() {
        while (Sim_system.running()) {
            Sim_event e = new Sim_event();
            sim_get_next(e);
            sim_process(delay);
            sim_trace(1,"DoneFuelTank");
            sim_completed(e);
            //System.out.print(Sim_system.sim_clock());
            localfueltank = 600;
            count++;
            b = true;
        }
    }
}
