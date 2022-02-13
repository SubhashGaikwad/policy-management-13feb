import { IVehicleDetails } from 'app/entities/vehicle-details/vehicle-details.model';
import { ParameterType } from 'app/entities/enumerations/parameter-type.model';

export interface IParameterLookup {
  id?: number;
  name?: number | null;
  type?: ParameterType | null;
  lastModified?: string;
  lastModifiedBy?: string;
  vehicleDetails?: IVehicleDetails | null;
}

export class ParameterLookup implements IParameterLookup {
  constructor(
    public id?: number,
    public name?: number | null,
    public type?: ParameterType | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public vehicleDetails?: IVehicleDetails | null
  ) {}
}

export function getParameterLookupIdentifier(parameterLookup: IParameterLookup): number | undefined {
  return parameterLookup.id;
}
