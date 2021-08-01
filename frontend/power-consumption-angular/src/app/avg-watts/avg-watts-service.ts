import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import { AvgWatts } from './avg-watts-model';

declare var EventSource;

@Injectable()
export class AvgWattsService {

    constructor() {
    }

    observeMessages(sseUrl: string): Observable<AvgWatts> {
        return new Observable<AvgWatts>(obs => {
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