import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRider } from '../rider.model';

@Component({
  selector: 'jhi-rider-detail',
  templateUrl: './rider-detail.component.html',
})
export class RiderDetailComponent implements OnInit {
  rider: IRider | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rider }) => {
      this.rider = rider;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
