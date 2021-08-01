import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import {AvgWattsService} from './avg-watts-service';
import {Observable, Subscription} from 'rxjs';
import { AppPersistenceService } from '../app-persistence.service';
import { AvgWatts } from './avg-watts-model';

@Component({
  selector: 'app-avg-watts',
  templateUrl: './avg-watts.component.html',
  styleUrls: ['./avg-watts.component.css']
})
export class AvgWattsComponent implements OnInit {
    private sseStream: Subscription;


   @Input() data: AvgWatts[] = [];


  constructor(private avgWattsService: AvgWattsService, private appPersistenceService: AppPersistenceService) { }

  ngOnInit(): void {
    this.sseStream = this.avgWattsService.observeMessages('http://localhost:8004/v1/energy-data-stream')
    .subscribe(avgWattEvent => {
        this.appPersistenceService.addDataAvgWatts(avgWattEvent);
    });

    this.data = this.appPersistenceService.getAvgWattData();

    this.appPersistenceService.dataAvgWattsChanged.subscribe(
        
        (dataChanged: AvgWatts[]) => {this.data = dataChanged;}
        
      );

  }

  ngOnDestroy(): void {
    if (this.sseStream) {
        this.sseStream.unsubscribe();
    }
  }

}
