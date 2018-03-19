
import { Injectable } from '@angular/core';
import {Http, Response, URLSearchParams, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/map';
import {Observable} from "rxjs";
import { Platform } from 'ionic-angular';
/*
 See https://angular.io/docs/ts/latest/guide/dependency-injection.html
 for more info on providers and Angular 2 DI.
 */
@Injectable()

export class EsqHttpClient {

  constructor(public http: Http, public platform: Platform)
  {
  }

  getData<T>(jsonDict:any):Observable<T>
  {
    if(!jsonDict || (typeof jsonDict != 'object') )
    {
      return Observable.throw("无效的请求参数: " + jsonDict );
    }
    let newJsonDict = jsonDict;

    let url = "";
    if(jsonDict["jsonFile"] && jsonDict["jsonFile"].length > 0)
    {
      url = jsonDict["jsonFile"];
      return this.getFromJsonFile(url);
    }

  }

    postData<T>(jsonDict:any):Observable<T>
  {
    if(!jsonDict || (typeof jsonDict != 'object') )
    {
      return Observable.throw("无效的请求参数: " + jsonDict );
    }

    let newJsonDict = jsonDict;

    let url = "";
    let body ="";
    if(jsonDict["jsonFile"] && jsonDict["jsonFile"].length > 0)
    {
      url = jsonDict["jsonFile"];
      body = jsonDict["body"];
      return this.postFromJson(url,body);
    }

  }

  private getFromJsonFile(url)
  {
    const myHeaders: Headers = new Headers();
    myHeaders.append("Content-Type", "application/json");
    return this.http.get(url,{ headers: myHeaders }).map(res=>{
      return res.json();
    }).catch(this.handleError);
  }

  private postFromJson(url,body){
    const myHeaders: Headers = new Headers();
    myHeaders.append("Content-Type", "application/json");
    return this.http.post(url,body,{ headers: myHeaders }).map(res=>{
      return res.json();
    }).catch(this.handleError);
  }
  private handleError (error: Response | any) {
    console.error("origin error: " + error);
    // In a real world app, we might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      errMsg = error.toString();
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    alert(errMsg);
    console.error("error: " + errMsg);
    return Observable.throw(errMsg);
  }
}
