package fuelstationrefueling;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;
import static fuelstationrefueling.FeulStationRefueling.*;
import java.util.Random;

public class Source extends Sim_entity {

    Sim_port out1, out2, out3;
    Sim_negexp_obj delay;
    int arr[] = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
    Random generator = new Random();
    int j;

    public Source(String name, double mean) {
        super(name);
        out1 = new Sim_port("out1");
        add_port(out1);
        out2 = new Sim_port("out2");
        add_port(out2);
        out3 = new Sim_port("out3");
        add_port(out3);
        delay = new Sim_negexp_obj("delay", mean);
        add_generator(delay);
    }

    @Override
    public void body() {
        for (int i = 1; i <= 1200; i++) {
            j = generator.nextInt(arr.length);
            if (fuelPump2.sim_waiting()+fuelPump2.k >= fuelPump1.sim_waiting()+fuelPump1.k && fuelPump3.sim_waiting()+fuelPump3.k >= fuelPump1.sim_waiting()+fuelPump1.k) {
                sim_schedule(out1, 0.0, 1, (int) arr[j]);
                sim_trace(1, "pump1 selected "+arr[j]);
            } else if (fuelPump1.sim_waiting()+fuelPump1.k >= fuelPump2.sim_waiting()+fuelPump2.k && fuelPump3.sim_waiting()+fuelPump3.k >= fuelPump2.sim_waiting()+fuelPump2.k) {
                sim_schedule(out2, 0.0, 1, (int) arr[j]);
                sim_trace(1, "pump2 selected "+arr[j]);
            } else if (fuelPump1.sim_waiting()+fuelPump1.k >= fuelPump3.sim_waiting()+fuelPump3.k && fuelPump2.sim_waiting()+fuelPump2.k >= fuelPump3.sim_waiting()+fuelPump3.k){
                sim_schedule(out3, 0.0, 1, (int) arr[j]);
                sim_trace(1, "pump3 selected "+arr[j]);
            }
            else{
                sim_schedule(out1, 0.0, 1, (int) arr[j]);
                sim_trace(1, "pump1 selected");
            }
            sim_pause(delay.sample());
            //System.out.print("l="+arr[j]+"\n");
        }
    }
}
