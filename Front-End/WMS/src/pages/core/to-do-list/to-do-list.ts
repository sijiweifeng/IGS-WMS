import { Component } from '@angular/core';
import { App, IonicPage, NavController, NavParams, ViewController } from 'ionic-angular';
import { ScanGarmentPage } from '../Receipt/Garment/scan-garment/scan-garment';
import { ScanPage } from '../Receipt/Fabric/scan/scan';
import { PrintPage} from '..//../print/print';
import {RoundProgressModule} from 'angular-svg-round-progressbar';

/**
* Generated class for the ToDoListPage page.
*
* See https://ionicframework.com/docs/components/#navigation for more info on
* Ionic pages and navigation.
*/

@IonicPage()
@Component({
  selector: 'page-to-do-list',
  templateUrl: 'to-do-list.html',
})
export class ToDoListPage {
  GRNDataSet1: any = [];
  pushPage: any;
  workflowSet:any =[];
  tabBarElement: any;
  terms:string;
  acc:any;
  index:any;
  showcontent:boolean = false;

  constructor(
    public viewCtrl: ViewController,
    public navCtrl: NavController,
    public navParams: NavParams,
    public appCtrl: App,
  )
  {
    this.tabBarElement = document.querySelector('#tabs ion-tabbar-section');
    this.pushPage = ScanGarmentPage;
    // this.getToDoList();
    this.GRNDataSet1 = [
    { id:'GEG-G-GRN-201801-0001',Qty:120,Cartons:10,Rolls:0,type:"Garment Receipt Request",percentage:33,priority:1,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-G-GRN-201801-0002',Qty:240,Cartons:20,Rolls:0,type:"Garment Receipt Request",percentage:66,priority:2,upcoming:"Scan UCC in Warehourse",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-GRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Fabric Receipt Request",percentage:66,priority:3,upcoming:"Scan UCC in Warehourse",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-GRN-201801-0002',Qty:480,Cartons:0,Rolls:10,type:"Fabric Receipt Request",percentage:66,priority:2,upcoming:"Scan UCC in Warehourse",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-G-SRN-201801-0001',Qty:120,Cartons:10,Rolls:0,type:"Garment Receipt Request",percentage:66,priority:1,upcoming:"Scan UCC in Warehourse",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-G-SRN-201801-0002',Qty:240,Cartons:10,Rolls:0,type:"Garment Receipt Request",percentage:66,priority:3,upcoming:"Scan UCC in Warehourse",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Fabric Receipt Request",percentage:33,priority:2,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Fabric Receipt Request",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Stock Counting",percentage:33,priority:2,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Stock Counting",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Stock Counting",percentage:33,priority:2,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Stock Counting",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Fabric Receipt Request",percentage:33,priority:2,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Warehouse Transfer",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Warehouse Transfer",percentage:33,priority:2,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Warehouse Transfer",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Location Transfer",percentage:33,priority:2,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Location Transfer",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0001',Qty:120,Cartons:0,Rolls:10,type:"Location Transfer",percentage:33,priority:2,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Issue",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Quality Assurance",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Quality Assurance",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Quality Assurance",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},
    { id:'GEG-F-SRN-201801-0002',Qty:240,Cartons:0,Rolls:20,type:"Issue",percentage:33,priority:4,upcoming:"Print Label",icon: 'ios-add-circle-outline',showDetails:false},

  ];

  this.workflowSet =[
    {name: "Garment Receipt Request"},
    {name: "Fabric Receipt Request"},
    {name: "Stock Counting"},
    {name: "Warehouse Transfer"},
    {name: "Location Transfer"},
    {name: "Quality Assurance"},
    {name: "Issue"}

  ];

}

toggleListItem(toggleID:string){
  let str1 = "#";
  let str2 = toggleID;
  let str3 = str1.concat(str2);
  let find = <HTMLScriptElement>document.querySelector(str3);

  if (find.style.display == 'none')
   {
    find.style.display = 'block';
  }else
  {
    find.style.display="none";
  }

  ;
}



// getToDoList() {
//   console.log("get to do list begin");
//   let jsonFile = "http://192.168.103.66:8090/GRN/getAllTask";
//   let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0 };
//   this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
//     if (itemGroup) {
//       console.log(JSON.stringify(itemGroup.result));
//       this.GRNDataSet1.push(...itemGroup.result);
//     }
//     console.log("get to do list end!");
//   }, (errMsg) => {
//     console.log(errMsg);
//   })
//
// }

toggleDetails(data) {
  // if (data.showDetails) {
  //   data.showDetails = false;
  //   data.icon = 'ios-add-circle-outline';
  // } else {
  //   data.showDetails = true;
  //   data.icon = 'ios-remove-circle-outline';
  // }
  console.log(data)  ;
};



determinePushPage(type: string,parameter:string,upcoming:string) {
  console.log("Determining Page to go[" + type +"] parameter:" + parameter);
  if (type === "Garment Receipet Request" && upcoming ==="Scan UCC in Warehourse") {
    this.pushPage = ScanGarmentPage;
  }
  else if (type === "Fabric Receipt Request" && upcoming ==="Scan UCC in Warehourse")
  {
    this.pushPage = ScanPage;
  }
  else if(upcoming="Print Label")
  {
    this.pushPage = PrintPage;
  }
  this.HidetabBar();
  this.navCtrl.push(this.pushPage,parameter);
}

HidetabBar() {
  let elements = document.querySelectorAll(".tabbar");
  if (elements != null) {
    Object.keys(elements).map((key) => {
      elements[key].style.display = 'none';
    });
  }

}
}
