package com.strayhelp.backend.model;

public enum PetStatus {
    REPORTED,    // stray case reported, not yet verified
    VERIFIED,    // report confirmed / case accepted
    RESCUED,     // picked up and currently in care
    AVAILABLE,   // ready and listed for adoption
    ADOPTED,     // successfully rehomed
    CLOSED       // duplicate, false report, deceased, or otherwise resolved
}
