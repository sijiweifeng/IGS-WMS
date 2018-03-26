import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController } from 'ionic-angular';
import { AppConfig } from "../../../app/app.config";
import { EsqCacheHelper } from '../../../providers/cacheHelper';
import { EsqHttpClient } from "../../../providers/HttpClient";

/**
 * Generated class for the SettingPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-setting',
  templateUrl: 'setting.html',
})
export class SettingPage {
  factory: String
  warehouse: String
  language: String
  type :String

  recordMaster :any=[]
  recordFactory: any=[]
  recordType: any=[]
  recordWarehouse: any=[]

  constructor(public navCtrl: NavController, public navParams: NavParams, private alertCtrl: AlertController,public httpclient: EsqHttpClient,public esqCache: EsqCacheHelper) {
    this.factory = esqCache.getKeyValue("factoryCode")
    this.initMaster();
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SettingPage');
  }

  initMaster() {
    console.log("get master data begin");
    let jsonFile = AppConfig.getBackEndUrl() + "/Inquiry/Search";
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0, "body": "query MasterQuery { Factory{id,factoryCode,Type{id,name},location{id,locationNo}}  }" };
    this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        console.log(JSON.stringify(itemGroup.Factory));
        this.recordMaster = itemGroup.Factory;
        this.recordFactory = itemGroup.Factory;
        this.recordWarehouse = itemGroup.Factory.location;
      }
      console.log("get master data end!");
    }, (errMsg) => {
      console.log(errMsg);
    })
  }
selectFactory(){
  for(let i=0;i<this.recordFactory.length;i++){
      if(this.factory===this.recordFactory[i].id){
        this.recordWarehouse = this.recordFactory[i].location;
        this.recordType = this.recordFactory[i].Type;
      }
    }
}

  save() {
    for(let i=0;i<this.recordFactory.length;i++){
      if(this.factory===this.recordFactory[i].id){
        this.esqCache.setKeyValue("factory",this.recordFactory[i]);
      }
    }

    for(let i=0;i<this.recordWarehouse.length;i++){
      if(this.warehouse===this.recordWarehouse[i].id){
        this.esqCache.setKeyValue("warehouse",this.recordWarehouse[i]);
      }
    }
    
    for(let i=0;i<this.recordType.length;i++){
      if(this.type===this.recordType[i].id){
        this.esqCache.setKeyValue("type",this.recordType[i]);
      }
    }

    this.esqCache.setKeyValue("language",this.language);

    let saveMsg = this.alertCtrl.create({
      title: "Setting",
      subTitle: "Save successfully!",
      buttons: [{
        text: 'OK',
        handler: () => {
          this.exitPage();
        }
      }
      ]
    });
    saveMsg.present();

  }
  exitPage() {
    console.log('Exit');
  }
}
