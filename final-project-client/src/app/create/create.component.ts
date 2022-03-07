import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators  } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from '../product-model';
import { TeleService } from '../tele.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  prodForm!: FormGroup;

  constructor(private fb: FormBuilder, private teleSvc: TeleService, private router: Router) { }

  ngOnInit(): void {
    this.prodForm = this.createSubForm();
  }

  createSubForm(): FormGroup {
    return this.fb.group({
      prodName: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      prodDesc: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      unitAmount: this.fb.control('', [Validators.required, Validators.min(1)]),
      interval: this.fb.control('', [Validators.required]),
      cardNum: this.fb.control('', [Validators.required, Validators.minLength(16), Validators.pattern("[0-9]*")]),
      cardExpMth: this.fb.control('', [Validators.required, Validators.min(1), Validators.max(12)]),
      cardExpYear: this.fb.control('', [Validators.required, Validators.min(2022)]),
      cardCvc: this.fb.control('', [Validators.required, Validators.minLength(3), Validators.maxLength(3),
          Validators.pattern("[0-9]*")])
    })
  }

  submit() {
    const product = this.prodForm.value as Product;
    console.info(product);
    this.teleSvc.postProduct(product)
      .then(v => console.info(v))
      .catch(error => alert(`Error: ${JSON.stringify(error)}`));
    // this.prodForm.reset();
    // this.router.navigate(['/display'])
  }

}
