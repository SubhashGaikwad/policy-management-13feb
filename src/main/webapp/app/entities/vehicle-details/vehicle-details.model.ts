import { IParameterLookup } from 'app/entities/parameter-lookup/parameter-lookup.model';
import { Zone } from 'app/entities/enumerations/zone.model';

export interface IVehicleDetails {
  id?: number;
  name?: number | null;
  invoiceValue?: string | null;
  idv?: string | null;
  enginNumber?: string | null;
  chassisNumber?: string | null;
  registrationNumber?: string | null;
  seatingCapacity?: number | null;
  zone?: Zone | null;
  yearOfManufacturing?: string | null;
  registrationDate?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  parameterLookups?: IParameterLookup[] | null;
}

export class VehicleDetails implements IVehicleDetails {
  constructor(
    public id?: number,
    public name?: number | null,
    public invoiceValue?: string | null,
    public idv?: string | null,
    public enginNumber?: string | null,
    public chassisNumber?: string | null,
    public registrationNumber?: string | null,
    public seatingCapacity?: number | null,
    public zone?: Zone | null,
    public yearOfManufacturing?: string | null,
    public registrationDate?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public parameterLookups?: IParameterLookup[] | null
  ) {}
}

export function getVehicleDetailsIdentifier(vehicleDetails: IVehicleDetails): number | undefined {
  return vehicleDetails.id;
}
