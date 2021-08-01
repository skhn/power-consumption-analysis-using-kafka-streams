import { EventEmitter, Injectable } from "@angular/core";
import { AvgWatts } from "./avg-watts/avg-watts-model";
import { SubDevices } from "./sub-devices/sub-devices.model";

@Injectable()
export class AppPersistenceService {

    dataAvgWattsChanged = new EventEmitter<AvgWatts[]>();

    private dataAvgWatts: AvgWatts[] = [];

    dataSubDevicesChanged = new EventEmitter<SubDevices[]>();

    private dataSubDevices: SubDevices[] = [];


    getAvgWattData() {
        return this.dataAvgWatts.slice();
    }

    getSubDevicesData() {
        return this.dataSubDevices.slice();
    }

    addDataAvgWatts(data: AvgWatts) {
        if (data !== null && (data.dateTime !== "--" && data.avgWatts !== 0)) {
            this.dataAvgWatts.push(data);
            console.log("pushed data = " + data)
        }

        this.dataAvgWattsChanged.emit(this.dataAvgWatts.slice());
    }
    addDataSubDevices(data: SubDevices) {
        if (data !== null && (data.Date !== "--" && data.Time !== "--")) {
            this.dataSubDevices.push(data);
            console.log("pushed data = " + data)
        }

        this.dataSubDevicesChanged.emit(this.dataSubDevices.slice());

    }
}