package fuelstationrefueling;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_from_p;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_stat;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import static fuelstationrefueling.FeulStationRefueling.*;

public class FuelPump extends Sim_entity {

    Sim_port out, in;
    Sim_normal_obj delay;
    int liters;
    static double ordertime, currenttime;
    public int k = 0;
    Sim_stat stat;

    public FuelPump(String name, double mean, double var) {
        super(name);
        stat = new Sim_stat();
        in = new Sim_port("in");
        add_port(in);
        out = new Sim_port("out");
        add_port(out);
        delay = new Sim_normal_obj("delay", mean, var);
        add_generator(delay);
        stat.add_measure(Sim_stat.THROUGHPUT);
        stat.add_measure(Sim_stat.QUEUE_LENGTH);
        stat.add_measure(Sim_stat.UTILISATION);
        set_stat(stat);
    }

    @Override
    public void body() {
        while (Sim_system.running()) {
            Sim_event e = new Sim_event();
            sim_get_next(new Sim_from_p(Sim_system.get_entity_id("source")), e);
            if (e.get_data() != null) {
                liters = (int) e.get_data();
                //System.out.print(Sim_system.sim_clock() + "nn\n");
                while (true) {
                    if (localfueltank - liters >= 0) {
                        localfueltank -= liters;
                        k = 1;
                        sim_process(delay.sample() + liters * 0.1);
                        sim_completed(e);
                        k = 0;
                        sim_trace(1, "process " + liters + "L " + (localfueltank) + "L in if");
                        break;
                    } else if (b == true) {
                        k = 1;
                        ordertime = (double) Sim_system.sim_clock();
                        sim_schedule(out, 0.0, 1);
                        sim_trace(1, "orderFuelTank");
                        b = false;
                        if (localfueltank - liters >= 0) {
                            localfueltank -= liters;
                            sim_process(delay.sample() + liters * 0.1);
                            sim_completed(e);
                            k = 0;
                            sim_trace(1, "process " + liters + "L " + (localfueltank) + "L in else if");
                            break;
                        }
                    } else {
                        k = 1;
                        /*currenttime = (double) Sim_system.sim_clock();
                        System.out.print(ordertime + "OO\n" + currenttime + "CC\n");
                        sim_pause(36000.0 - (currenttime - ordertime) % 36000.0);*/
                        sim_pause(1);
                        if (localfueltank - liters >= 0) {
                            localfueltank -= liters;
                            sim_process(delay.sample() + liters * 0.1);
                            sim_completed(e);
                            k = 0;
                            sim_trace(1, "process " + liters + "L " + (localfueltank) + "L in Else");
                            break;
                        }
                    }
                }
            }
        }
    }
}
