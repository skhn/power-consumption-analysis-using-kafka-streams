import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import { AvgWatts } from '../avg-watts/avg-watts-model';
import { SubDevices } from './sub-devices.model';

declare var EventSource;

@Injectable()
export class SubDeviceService {

    constructor() {
    }

    observeMessages(sseUrl: string): Observable<SubDevices> {
        return new Observable<SubDevices>(obs => {
            const es = new EventSource(sseUrl);
            es.addEventListener('message', (evt) => {
                var eventData =  JSON.parse(evt.data);
                console.log(eventData);
                obs.next(eventData);
            });
            return () => es.close();
        });
    }
}