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
  language: string
  type :String


  recordMaster :any=[]
  recordFactory: any=[]
  recordType: any=[]
  recordWarehouse: any=[]
  recordLanguage :any=[{name:"English"},{name:"中文"}];
  constructor(public navCtrl: NavController, public navParams: NavParams, private alertCtrl: AlertController,public httpclient: EsqHttpClient,public esqCache: EsqCacheHelper) {
        this.initMaster();
  this.esqCache.getKeyValue("factory").catch((data)=>{
      console.log(data);
    }).then((data)=>{
      if(data!=undefined){
        this.factory = data.id;
        this.selectFactory();
      }
    });
  this.esqCache.getKeyValue("type").catch((data)=>{
      console.log(data);
    }).then((data)=>{
      if(data!=undefined){
        this.type = data.id;
      }
    });

  this.esqCache.getKeyValue("warehouse").catch((data)=>{
      console.log(data);
    }).then((data)=>{
      if(data!=undefined){
        this.warehouse = data.id;
      }
    });
  this.esqCache.getKeyValue("language").catch((data)=>{
      console.log(data);
    }).then((data)=>{
      if(data!=undefined){
        this.language = data;
      }
    });    
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
