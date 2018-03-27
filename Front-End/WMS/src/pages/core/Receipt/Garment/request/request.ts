import { Component } from '@angular/core';
import { NavController, NavParams, ModalController, AlertController } from 'ionic-angular';
import { WorkflowcontrolPage } from "../../../workflowcontrol/workflowcontrol";
import { EsqHttpClient } from "../../../../../providers/HttpClient";
import { StatusBar } from '@ionic-native/status-bar';
import { ConfirmLocationGarmentPage } from '../cfm-location-garment/cfm-location-garment';
import { UUID } from 'angular2-uuid';
import { AppConfig } from "../../../../../app/app.config";
import { EsqCacheHelper } from "../../../../../providers/cacheHelper";

@Component({
  selector: 'page-request',
  templateUrl: 'request.html'
})
export class RequestPage {
  storeCode: string;
  transDate: Date;
  reqType: string;
  businessTypeId: string;

  nextPage: any;
  enterInput: any;
  dataset1: any = [];
  waitItems: any = [];
  leavePage: any;

  addFlag: Boolean;
  findFlag: Boolean;

  factoryCode: string;
  factoryId: string;
  type: string;
  typeId: string;
  warehouse: string;
  warehouseId: string;

  constructor(
    private statusBar: StatusBar,
    public navCtrl: NavController,
    public navParams: NavParams,
    public modalCtrl: ModalController,
    public httpclient: EsqHttpClient,
    private alertCtrl: AlertController,
    private esqCache: EsqCacheHelper) {
    // If we navigated to this page, we will have an item available as a nav param
    this.storeCode = navParams.get('storeCode');
    this.reqType = navParams.get("reqType");
    this.transDate = navParams.get("transDate");
    this.businessTypeId = navParams.get("businessTypeId");

    this.leavePage = WorkflowcontrolPage;
    this.nextPage = ConfirmLocationGarmentPage;
    this.statusBar.overlaysWebView(true);
    this.statusBar.show();
    this.statusBar.backgroundColorByHexString('#FFAABB');

    esqCache.getKeyValue("factory").then((data) => {
      this.factoryCode = data.factoryCode;
      this.factoryId = data.factoryId;
    });
    esqCache.getKeyValue("type").then((data) => {
      this.typeId = data.id;
      this.type = data.name;
    });

    esqCache.getKeyValue("warehouse").then((data) => {
      this.warehouse = data.locationNo;
      this.warehouseId = data.id;
    });

    this.getWaitItems();

  }

  getWaitItems() {
    console.log("get easn wait sent to warehouse ucc");
    let jsonFile = AppConfig.getBackEndUrl() + "/Inquiry/getEASNSacnData";
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0 };
    this.httpclient.getData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        console.log(JSON.stringify(itemGroup.result));
        this.waitItems.push(...itemGroup.result);
      }
      console.log("get easn wait sent to warehouse ucc end!");
    }, (errMsg) => {
      console.log(errMsg);
    })

  }

  getFormatDate(time: any) {

    // 格式化日期，获取今天的日期
    const Dates = new Date(time);
    const year: number = Dates.getFullYear();
    const month: any = (Dates.getMonth() + 1) < 10 ? '0' + (Dates.getMonth() + 1) : (Dates.getMonth() + 1);
    const day: any = Dates.getDate() < 10 ? '0' + Dates.getDate() : Dates.getDate();
    const hh: any = Dates.getHours();
    const mm: any = Dates.getMinutes();
    const ss: any = Dates.getSeconds();

    return year + '-' + month + '-' + day + " " + hh + ":" + mm + ":" + ss;
  }

  searchBarcode(event) {
    if (event && event.keyCode == 13) {
      console.log("EASN search:" + this.enterInput);
      this.addFlag = true;
      for (let j = 0; j < this.dataset1.length; j++) {
        if (this.enterInput === this.dataset1[j].ucc) {
          this.addFlag = false;
          this.duplicateAlert();
          break;
        }
      }
      if (this.addFlag) {
        this.findFlag = false;
        for (let i = 0; i < this.waitItems.length; i++) {
          if (this.enterInput === this.waitItems[i].ucc) {
            let uuid = UUID.UUID();
            this.waitItems[i]["id"] = uuid;
            this.dataset1.push(this.waitItems[i]);
            this.findFlag = true;
          }
        }
        if (!this.findFlag) {
          this.presentAlert();
        }
      }
      this.enterInput = "";
    }
  }

  presentAlert() {
    let alert = this.alertCtrl.create({
      title: 'Barcode Not match',
      subTitle: 'Your input is not found',
      buttons: ['Dismiss']
    });
    alert.present();
  }


  duplicateAlert() {
    let alert = this.alertCtrl.create({
      title: "Duplicate scanning.",
      subTitle: "Duplicate",
      buttons: ['Dismiss']
    });
    alert.present();
  }

  save() {
    //this.navCtrl.push(this.nextPage);
    console.log("save begin");
    let uuid = UUID.UUID();
    let jsonFile = AppConfig.getBackEndUrl() + "/Receipt/Create";
    let grn = {
      "receipt": {
        "UniqueKey": "docNo", "transCode": "KBR", "transId": uuid, "docNo": "GEG-G-GRN-" + new Date().getTime(),
        "storeCode": this.storeCode, "transDate": this.transDate,
        "reqUserId": "zhanghonl", "reqDate": this.getFormatDate(new Date().getTime()), "status": "D",
        "stock": this.dataset1
      },
      "workflow": {
        "businessTypeId": this.businessTypeId, "factoryId": this.factoryId, "factoryCode": this.factoryCode,
        "type": this.type, "typeId": this.typeId, "warehouse": this.warehouse, "warehouseId": this.warehouseId
      }
    };
    console.log(grn);
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0, "body": grn };
    this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        let saveMsg = this.alertCtrl.create({
          title: "GRN",
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
        console.log(JSON.stringify(itemGroup.result));
      }
      console.log("get easn wait sent to warehouse ucc end!");
    }, (errMsg) => {
      console.log(errMsg);
    })

  }

  ShowtabBar() {
    let elements = document.querySelectorAll(".tabbar");
    if (elements != null) {
      Object.keys(elements).map((key) => {
        elements[key].style.display = 'flex';
      });
    }

  }
  exitPage() {
    console.log('Exit');
    this.ShowtabBar();
    this.navCtrl.pop();

  }
}
