import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController } from 'ionic-angular';
import { FileChooser } from '@ionic-native/file-chooser';
import { EsqHttpClient } from "../../../../../providers/HttpClient";
import { EsqCacheHelper } from "../../../../../providers/cacheHelper";
import { WorkflowcontrolPage } from "../../../workflowcontrol/workflowcontrol";
import { RequestPage } from "../request/request";
import { AppConfig } from "../../../../../app/app.config";

/**
* Generated class for the InitGarmentPage page.
*
* See https://ionicframework.com/docs/components/#navigation for more info on
* Ionic pages and navigation.
*/

@IonicPage()
@Component({
  selector: 'page-init-garment',
  templateUrl: 'init-garment.html',
})
export class InitGarmentPage {
  recordStore: any = [];
  parameter: string;
  parameterList: string[];
  reqType: any;
  leavePage: any;
  transDate: Date;
  storeCode:string;
  factoryCode:string;
  typeid:string;
  businessTypeId:string;

  constructor(public navCtrl: NavController, public navParams: NavParams, private fileChooser: FileChooser,
    public httpclient: EsqHttpClient, private alertCtrl: AlertController,private esqCache: EsqCacheHelper) {
    this.leavePage = WorkflowcontrolPage;
    this.parameter = navParams.get('parameter');
    this.parameterList = this.parameter.split(";")
    if (this.parameterList === null || this.parameterList === undefined) {
      let tempList = this.parameter.split(":");
      if (tempList[0] === "reqType") {
        this.reqType = tempList[1]
      }
    } else {
      for (let i = 0; i < this.parameterList.length; i++) {
        let tempList = this.parameterList[i].split(":");
        if (tempList[0] === "reqType") {
          this.reqType = tempList[1]
        }
      }
    }
    esqCache.getKeyValue("factory").then((data)=>{
      this.factoryCode = data.factoryCode;
      esqCache.getKeyValue("type").then((data)=>{
        this.typeid = data.id;
        this.initStore(this.factoryCode,this.typeid);
      })
    })
    
    this.transDate = new Date();
  }

  initStore(factoryCode,typeid) {
    console.log("get store list begin");
    let jsonFile = AppConfig.getBackEndUrl() + "/Inquiry/Search";
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0, "body": "query storeQuery { Type(id:\"" + typeid + "\"){store(factoryCode:\""+ factoryCode +"\"){storeCode} }}" };
    this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        console.log(JSON.stringify(itemGroup));
        this.recordStore = itemGroup.Type[0].store;
      }
      console.log("get store list end!");
    }, (errMsg) => {
      console.log(errMsg);
    })
  }
  openFileChooser() {
    this.fileChooser.open()
      .then(uri => console.log(uri))
      .catch(e => console.log(e));

  }


  ionViewDidLoad() {
    console.log('ionViewDidLoad InitGarmentPage');
  }
  next() {
    console.log("check trans date begin" + this.transDate);
    let jsonFile = AppConfig.getBackEndUrl() + "/Inquiry/checkTransDateFrozen";
    let grn = { type: "Garment", factoryCode: "GEG", "transDate": this.transDate };

    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0, "body": grn };
    this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        console.log(JSON.stringify(itemGroup.result));
        if (itemGroup.result === null) {
          let checkMsg = this.alertCtrl.create({
            title: "Trans Date check",
            subTitle: itemGroup.message,
            buttons: [{
              text: 'OK'
            }
            ]
          });
          checkMsg.present();
          console.log(itemGroup.message);
        }
        else {
          this.navCtrl.push(RequestPage, { reqType: this.reqType, transDate: this.transDate ,storeCode:this.storeCode ,businessTypeId:this.businessTypeId});
        }
        //this.GRNDataSet1.push(...itemGroup.result);
      }
      console.log("check trans date end!");
    }, (errMsg) => {
      console.log(errMsg);
    })
  }
}
