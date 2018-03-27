import { Injectable } from '@angular/core';
import { CacheService } from 'ionic-cache';

@Injectable()

export class EsqCacheHelper {

    constructor(public cache: CacheService) {

    }

    public setKeyValue(key, value) {
        if (this.cache.itemExists(key)) {
            this.cache.removeItem(key);
        }
        this.cache.getRawItem(key).catch(() => {
            let result = value;
            return this.cache.saveItem(key, result);
        }).then((data) => {
            console.log("Saved data: ", data);
        });
    }

    public getKeyValue(key): Promise<any> {
        return this.cache.getRawItem(key).catch((data)=>{
            return Promise.reject(data);
        }).then(function(data){
            let obj = JSON.parse(data.value)
            return obj;
        })
    }
}