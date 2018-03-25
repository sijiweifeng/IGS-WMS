import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController } from 'ionic-angular';
import { FileChooser } from '@ionic-native/file-chooser';
import { EsqHttpClient } from "../../../../../providers/HttpClient";
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

  constructor(public navCtrl: NavController, public navParams: NavParams, private fileChooser: FileChooser,
    public httpclient: EsqHttpClient, private alertCtrl: AlertController) {
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
    this.initStore();
    this.transDate = new Date();
    console.log(this.transDate);
    //  console.log(this.reqType);
    //     this.recordStore=[
    //   {name:"StoreA"},
    //   {name:"StoreB"},
    //   {name:"StoreC"}
    // ];
  }

  initStore() {
    console.log("get store list begin");
    let jsonFile = AppConfig.getBackEndUrl() + "/Inquiry/Search";
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0, "body": "query GRNQuery { store{storeCode} }" };
    this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        console.log(JSON.stringify(itemGroup.store));
        this.recordStore = itemGroup.store;
        //this.GRNDataSet1.push(...itemGroup.result);
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
          this.navCtrl.push(RequestPage, { reqType: this.reqType, transDate: this.transDate, factoryCode: "GEG", type: "Garment" });
        }
        //this.GRNDataSet1.push(...itemGroup.result);
      }
      console.log("check trans date end!");
    }, (errMsg) => {
      console.log(errMsg);
    })
  }
}
