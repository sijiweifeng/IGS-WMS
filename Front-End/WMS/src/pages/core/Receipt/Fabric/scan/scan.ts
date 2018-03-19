import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController } from 'ionic-angular';
import { ToDoListPage } from '../../../to-do-list/to-do-list';
import { WorkflowcontrolPage } from '../../../workflowcontrol/workflowcontrol';
import { EsqHttpClient } from "../../../../../providers/HttpClient";
import { AppConfig } from "../../../../../app/app.config";

/**
* Generated class for the ScanPage page.
*
* See https://ionicframework.com/docs/components/#navigation for more info on
* Ionic pages and navigation.
*/

@IonicPage()
@Component({
  selector: 'page-scan',
  templateUrl: 'scan.html',
})
export class ScanPage {
  selectedItem: any;
  dataset1: Array<{ id: string, status: boolean, color: string, grade: string, shade: string, fabricdisplay: boolean, locationreadonly: boolean, location: string, recommendedlocation: string }>;
  pushPage: any;
  leavePage: any;
  editingUCC: any;
  enterInput: any;
  recommendedinstance: any;
  inputFoundinOrder: boolean;


  constructor(public navCtrl: NavController, public navParams: NavParams,
    private alertCtrl: AlertController,
    public httpclient: EsqHttpClient,
    private editAlertCtrl: AlertController) {
    // If we navigated to this page, we will have an item available as a nav param
    this.selectedItem = 54;// navParams.get('item');

    // this.dataset1 = [
    //   { id:'CGB180800109',status:false,color:'01(01)@01(01) Off',grade:'A',shade:'E1',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    //   { id:'CGB157600203',status:false,color:'01(01)@01(01) Off',grade:'A*',shade:'E1',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    //   { id:'CGB157600502',status:false,color:'01(01)@01(01) Off',grade:'A*',shade:'E3',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    //   { id:'CGB18080010A',status:false,color:'01(01)@01(01) Off',grade:'A',shade:'E1',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    //   { id:'CGB180800204',status:false,color:'01(01)@01(01) Off',grade:'A*',shade:'E2',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    //   { id:'CGB180800205',status:false,color:'01(01)@01(01) Off',grade:'A*',shade:'E1',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    //   { id:'CGB180600204',status:false,color:'01(01)@01(01) Off',grade:'A',shade:'E3',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    //   { id:'CGB157600302',status:false,color:'01(01)@01(01) Off',grade:'A*',shade:'E2',fabricdisplay:true,locationreadonly:true,location:null,recommendedlocation:null},
    // ];

    this.initDataSet();

  }

  initDataSet() {
    console.log("get to do list begin");
    let jsonFile = AppConfig.getBackEndUrl() +  "/GRN/getScanData/" + this.selectedItem;
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0 };
    this.httpclient.getData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        console.log(JSON.stringify(itemGroup.result));
        this.dataset1.push(...itemGroup.result);
      }
      console.log("get to do list end!");
    }, (errMsg) => {
      console.log(errMsg);
    })

  }

  editUCCLocation(UCCinput: string) {
    //for in page edit
    this.editingUCC = UCCinput;
    for (let i = 0; i < this.dataset1.length; i++) {
      this.dataset1[i].locationreadonly = true;
    }
    for (let i = 0; i < this.dataset1.length; i++) {
      if (this.editingUCC === this.dataset1[i].id) {
        this.dataset1[i].locationreadonly = false;
        break;
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
              if (UCCinput === this.dataset1[i].id) {
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

  checkBarcodeExist() {
    console.log(this.enterInput);
    console.log("enter the checkBarcodeExist")
    for (let i = 0; i < this.dataset1.length; i++) {
      if (this.enterInput === this.dataset1[i].id) {
        console.log("Find same id in the order" + this.enterInput);
        this.dataset1[i].status = true;
        this.inputFoundinOrder = true;
        this.recommendedinstance = this.dataset1[i].recommendedlocation;
        this.dataset1[i].location = this.dataset1[i].recommendedlocation;
        break;
      }
      else {
        console.log("Search item Not found");
      }

    };
    this.presentAlert();
  }

  save() {
    console.log("save begin");

    let jsonFile = AppConfig.getBackEndUrl() +  "/GRN/Scan/" + this.selectedItem;
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0, "body": this.dataset1 };
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
      console.log("save end!");
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
