import { Component } from '@angular/core';
import { NavController, NavParams, ModalController, AlertController } from 'ionic-angular';
import {WorkflowcontrolPage} from "../../../workflowcontrol/workflowcontrol";
import {ScanPage} from "../scan/scan";
import { EsqHttpClient } from "../../../../../providers/HttpClient";

@Component({
  selector: 'page-fabricRequest',
  templateUrl: 'fabricRequest.html'
})
export class FabricRequestPage {
  selectedItem: any;
  nextPage:any;
  enterInput: any;
  myParam: string;
  inputFoundinOrder: boolean;
  dataset1: any= [];
  waitItems: any = [];
  leavePage:any;

  addFlag: Boolean;
  findFlag:Boolean;
  constructor(
    public navCtrl: NavController, public navParams: NavParams,
    public modalCtrl: ModalController,
    public httpclient: EsqHttpClient,
    // private barcodeScanner: BarcodeScanner,
    private alertCtrl: AlertController) {
    // If we navigated to this page, we will have an item available as a nav param
    this.selectedItem = navParams.get('item');
    this.leavePage = WorkflowcontrolPage;
    this.nextPage = ScanPage;
    // Let's populate this page with some filler content for funzies
    this.inputFoundinOrder = false;
    // this.getWaitItems();
    this.enterInput = "WGC25580020B";
    this.myParam = "test";
  }


  // getWaitItems() {
  //   console.log("get easn wait sent to warehouse ucc");
  //   let jsonFile = "http://192.168.103.66:8091/Inquiry/SearchFabric";
  //   let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0 };
  //   this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
  //     if (itemGroup) {
  //       console.log(JSON.stringify(itemGroup.result));
  //       this.waitItems.push(...itemGroup.result);
  //     }
  //     console.log("get easn wait sent to warehouse ucc end!");
  //   }, (errMsg) => {
  //     console.log(errMsg);
  //   })
  //
  // }
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
  searchBarcode() {
    console.log("Fabric search:" + this.enterInput);
    this.addFlag = true;
    for (let j = 0; j < this.dataset1.length; j++) {
      if (this.enterInput === this.dataset1[j].ucc) {
        this.addFlag = false;
        this.duplicateAlert();
        break;
      }
    }
    if (this.addFlag) {
      this.findFlag=false;
      for (let i = 0; i < this.waitItems.length; i++) {
        if (this.enterInput === this.waitItems[i].ucc) {
          this.dataset1.push(this.waitItems[i]);
          this.findFlag=true;
        }
      }
      if(!this.findFlag){
        this.presentAlert();
      }
    }
  }

  presentAlert() {
    if (this.inputFoundinOrder == false) {
      let alert = this.alertCtrl.create({
        title: 'Barcode Not match',
        subTitle: 'Your input is not found',
        buttons: ['Dismiss']
      });
      alert.present();
    }
  }

  duplicateAlert(){
    let alert = this.alertCtrl.create({
      title:"Duplicate scanning.",
      subTitle:"Duplicate",
      buttons:['Dismiss']
    });
    alert.present();
  }

  ShowtabBar(){
    let elements = document.querySelectorAll(".tabbar");
    if (elements != null) {
      Object.keys(elements).map((key) => {
        elements[key].style.display = 'flex';
      });
    }

  }
  exitPage(){
    console.log('Exit');
    this.ShowtabBar();
    this.navCtrl.pop();

  }

//   save(){
//     this.navCtrl.push(this.nextPage);
//     console.log("save begin");
//
//     let jsonFile = "http://192.168.103.66:8090/GRN/FabricCreate";
//     let grn = {"GRN":[{GRNKey: "docNo", docNo:"GEG-F-GRN-" + new Date().getTime(), "reqUserId":"zhanghonl","reqDate":this.getFormatDate(new Date().getTime()),"stock":this.dataset1}]};
//     console.log(grn);
//     let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0,"body": grn };
//     this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
//       if (itemGroup) {
//         let saveMsg = this.alertCtrl.create({
//           title:"GRN",
//           subTitle:"Save successfully!",
//           buttons:[{
//             text:'OK',
//             handler:()=> {
//               this.exitPage();
//             }
//           }
//         ]
//       });
//       saveMsg.present();
//       console.log(JSON.stringify(itemGroup.result));
//     }
//     console.log("get fabric wait sent to warehouse ucc end!");
//   }, (errMsg) => {
//     console.log(errMsg);
//   })
//   console.log("End of save");
// }
}
