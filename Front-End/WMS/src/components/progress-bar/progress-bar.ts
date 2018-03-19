import { Component, Input } from '@angular/core';

@Component({
  selector: 'progress-bar',
  templateUrl: 'progress-bar.html'
})
export class ProgressBarComponent {

  ProgressBar:any;
  line :any ;
  bar: any;

  @Input('progress') progress;

  constructor() {

    // this.ProgressBar = require('progressbar.js');
    // this.line = new ProgressBar.Line('#container');
    // 
    // this.bar = new ProgressBar.Line(container, {
    //   strokeWidth: 4,
    //   easing: 'easeInOut',
    //   duration: 1400,
    //   color: '#FFEA82',
    //   trailColor: '#eee',
    //   trailWidth: 1,
    //   svgStyle: {width: '100%', height: '100%'},
    //   from: {color: '#FFEA82'},
    //   to: {color: '#ED6A5A'},
    //   step: (state, bar) => {
    //     bar.path.setAttribute('stroke', state.color);
    //   }
    // });
    //
    // this.bar.animate(1.0);  // Number from 0.0 to 1.0
  }

}
