import { Component, OnInit } from '@angular/core';
import { Status } from '../product-model';
import { TeleService } from '../tele.service';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit {

  statuses: Status[] = [];

  constructor(private teleSvc: TeleService) { }

  ngOnInit(): void {
    this.teleSvc.getStatus()
      .then(result => this.statuses = result)
      .catch(error => alert(`Error: ${JSON.stringify(error)}`))
  }

}
