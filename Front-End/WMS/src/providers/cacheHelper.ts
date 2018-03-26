import { Injectable } from '@angular/core';
import { CacheService } from 'ionic-cache';

@Injectable()

export class EsqCacheHelper {

    constructor(public cache: CacheService) {

    }

    public getKeyValue(key) {
        let value: any;
        if (this.cache.itemExists(key)) {
            console.log(this.cache.getItem(key));
            value = this.cache.getItem(key)
        }
        return value;
    }
    public setKeyValue(key, value) {
        this.cache.getItem(key).catch(() => {
            let result = value;
            return this.cache.saveItem(key, result);
        }).then((data) => {
            console.log("Saved data: ", data);
        });
    }
}