import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators  } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-sub',
  templateUrl: './create-sub.component.html',
  styleUrls: ['./create-sub.component.css']
})
export class CreateSubComponent implements OnInit {

  subForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.subForm = this.createSubForm();
  }

  createSubForm(): FormGroup {
    return this.fb.group({
      subName: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      subDesc: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      unitAmount: this.fb.control('', [Validators.required, Validators.min(1)]),
      interval: this.fb.control('', [Validators.required]),
      cardNum: this.fb.control('', [Validators.required, Validators.minLength(12), Validators.pattern("[0-9]*")]),
      cardExpMth: this.fb.control('', [Validators.required, Validators.min(1), Validators.max(12)]),
      cardExpYear: this.fb.control('', [Validators.required, Validators.min(2022)]),
      cardCvc: this.fb.control('', [Validators.required, Validators.minLength(3), Validators.pattern("[0-9]*")])
    })
  }

  subscribe() {

  }

}
