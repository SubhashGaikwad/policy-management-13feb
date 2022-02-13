import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankDetails } from '../bank-details.model';

@Component({
  selector: 'jhi-bank-details-detail',
  templateUrl: './bank-details-detail.component.html',
})
export class BankDetailsDetailComponent implements OnInit {
  bankDetails: IBankDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankDetails }) => {
      this.bankDetails = bankDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
