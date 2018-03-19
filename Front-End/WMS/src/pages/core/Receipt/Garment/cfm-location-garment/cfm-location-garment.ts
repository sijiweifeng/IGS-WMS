import { Component } from '@angular/core';
import { NavController, NavParams, ModalController, AlertController } from 'ionic-angular';
import { WorkflowcontrolPage } from '../../../workflowcontrol/workflowcontrol';
// import {BarcodeScanPage} from '../barcode-scan/barcode-scan';
// import { BarcodeScanner } from '@ionic-native/barcode-scanner';
import { EsqHttpClient } from "../../../../../providers/HttpClient";
import { AppConfig } from "../../../../../app/app.config";

@Component({
  selector: 'page-cfm-location-garment',
  templateUrl: 'cfm-location-garment.html'
})

export class ConfirmLocationGarmentPage {
  selectedItem: any;
  editingUCC: string;
  pushPage: any;
  enterInput: any;
  recommendedinstance: string;
  myParam: string;
  inputFoundinOrder: boolean;
  items: Array<{ title: string, note: string, icon: string }>;
  testDatadumb1:Array<{id:string,status:boolean,location:string,recommendedlocation:string,locationreadonly:boolean}>;
  dataset1: any = [];//Array<{id:string,status:boolean,location:string,recommendedlocation:string,locationreadonly:boolean}>;

  constructor(
    public navCtrl: NavController, public navParams: NavParams,
    public modalCtrl: ModalController,
    // private barcodeScanner: BarcodeScanner,
    private alertCtrl: AlertController,
    public httpclient: EsqHttpClient,
    private editAlertCtrl: AlertController) {
      this.pushPage = WorkflowcontrolPage;
      // If we navigated to this page, we will have an item available as a nav param
      this.selectedItem =this.navParams.data;// navParams.get('item');
      console.log("param:" + this.navParams.data);
      // Let's populate this page with some filler content for funzies
      this.inputFoundinOrder = false;

      this.testDatadumb1 = [
        { id:'32487928743981',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
        { id:'32487928743982',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
        { id:'32487928743983',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
        { id:'32487928743984',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
        { id:'32487928743985',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
        { id:'32487928743986',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
        { id:'32487928743987',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
        { id:'32487928743988',status:false,location:null,recommendedlocation:'A1-01-02-01-1',locationreadonly:true},
      ];
      // this.initDataSet();
      this.enterInput = null;
      this.items = [];
      this.myParam = "test";
    }

    // initDataSet() {
    //   console.log("get to do list begin");
    //   let jsonFile = "http://192.168.103.66:8090/GRN/getScanData/" + this.selectedItem;
    //   let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0 };
    //   this.httpclient.getData<any>(jsonDict).subscribe((itemGroup) => {
    //     if (itemGroup) {
    //       console.log(JSON.stringify(itemGroup.result));
    //       this.dataset1.push(...itemGroup.result);
    //     }
    //     console.log("get to do list end!");
    //   }, (errMsg) => {
    //     console.log(errMsg);
    //   })
    //
    // }

    checkBarcodeExist() {
      console.log(this.enterInput);
      console.log("enter the checkBarcodeExist")
      for (let i = 0; i < this.dataset1.length; i++) {
        if (this.enterInput === this.dataset1[i].ucc) {
          console.log("Find same ucc in the order" + this.enterInput);
          this.dataset1[i].status = true;
          this.inputFoundinOrder = true;
          this.recommendedinstance = this.dataset1[i].recommendedlocation;
          this.dataset1[i].location = this.dataset1[i].recommendedlocation;
          this.presentRecommendLocation();
          break;
        }
        else {
          console.log("Search item Not found");
        }

      };
      this.presentAlert();
    }


    editUCCLocation(UCCinput: string) {
      console.log(this.testDatadumb1);
      console.log(UCCinput);
      //for in page edit
      this.editingUCC = UCCinput;
      for (let i = 0; i < this.dataset1.length; i++) {
        this.dataset1[i].locationreadonly = true;
      }
      for (let i = 0; i < this.dataset1.length; i++) {
        if (this.editingUCC === this.dataset1[i].ucc) {
          this.dataset1[i].locationreadonly = false;
          break;
        }
      }
    }


    presentEditLocationPrompt(UCCinput: string) {
      this.editingUCC = UCCinput;
      let alert = this.editAlertCtrl.create({
        title: 'Edit UCC Location',
        inputs: [
          {
            name: 'UCC',
            placeholder: 'New Location For UCC',
          }
        ],
        buttons:
        [
          {
            text: 'Done',
            role: 'Done',
            handler: data => {


              console.log(data);

              for (let i = 0; i < this.dataset1.length; i++) {
                console.log("running");
                if (UCCinput === this.dataset1[i].ucc) {
                  console.log("running");
                  this.dataset1[i].location = data.UCC;
                  break;
                }
              }

            }
          },

        ]
      });
      alert.present();
    }


    generateRecommendLocationForUCC() {
      //write the function to calculate recommended location for specific ucc



    }


    presentRecommendLocation() {
      let alert = this.alertCtrl.create({
        title: 'Recommendation to UCC Location',
        subTitle: 'UCC Location is recommended to ' + this.recommendedinstance,
        buttons: ['Accept']
      });
      alert.present();
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

    openBasicModal() {
      console.log("open detail page");
    }

    save(){
      this.navCtrl.push(this.pushPage);

      console.log("save begin");

      let jsonFile = AppConfig.getBackEndUrl() +  "/GRN/Scan/" + this.selectedItem;
      let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0,"body": this.dataset1 };
      this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
        if (itemGroup) {
          let saveMsg = this.alertCtrl.create({
            title:"GRN",
            subTitle:"Save successfully!",
            buttons:[{
              text:'OK',
              handler:()=> {
                this.exitPage();
              }
            }
          ]
        });
        saveMsg.present();
        console.log(JSON.stringify(itemGroup.result));
      }
      console.log("save end!");
    }, (errMsg) => {
      console.log(errMsg);
    })
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
  // openScanner(){
  //   this.barcodeScanner.scan().then((barcodeData) => {
  //     // Success! Barcode data is here
  //     console.log(barcodeData);
  //   }, (err) => {
  //     console.log("Fail to read barcode");
  //   })
  //
  // }

}
