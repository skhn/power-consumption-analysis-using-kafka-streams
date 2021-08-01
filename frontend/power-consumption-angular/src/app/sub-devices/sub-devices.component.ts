import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AppPersistenceService } from '../app-persistence.service';
import { AvgWattsService } from '../avg-watts/avg-watts-service';
import { SubDevices } from './sub-devices.model';
import { SubDeviceService } from './sub-devices.service';

@Component({
  selector: 'app-sub-devices',
  templateUrl: './sub-devices.component.html',
  styleUrls: ['./sub-devices.component.css']
})
export class SubDevicesComponent implements OnInit {

    constructor(private subDeviceService: SubDeviceService, private appPersistenceService: AppPersistenceService) { }

    private sseStream: Subscription;

    @Input() data: SubDevices[] = [];

  ngOnInit(): void {
    this.sseStream = this.subDeviceService.observeMessages('http://localhost:8007/v1/sub-2-devices-data-stream')
    .subscribe(subDeviceEvent => {
        this.appPersistenceService.addDataSubDevices(subDeviceEvent);
    });

    this.data = this.appPersistenceService.getSubDevicesData();

    this.appPersistenceService.dataSubDevicesChanged.subscribe(
        
        (dataChanged: SubDevices[]) => {this.data = dataChanged;}
        
      );

  }

  ngOnDestroy(): void {
    if (this.sseStream) {
        this.sseStream.unsubscribe();
    }
  }

}
