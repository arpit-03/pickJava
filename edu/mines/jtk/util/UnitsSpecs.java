/*    */ package edu.mines.jtk.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class UnitsSpecs
/*    */ {
/* 32 */   static String[] specs = new String[] { "# BASE UNITS.  Should be first and are identified by an empty definition.", "#", "ampere                 P                       # electric current", "bit                    P                       # unit of information", "candela                P                       # luminous intensity", "kelvin                 P                       # thermodynamic temperature", "kilogram               P                       # mass", "meter                  P                       # length", "mole                   P                       # amount of substance", "second                 P                       # time", "radian                 P                       # plane angle", "", "#", "# CONSTANTS", "#", "percent                S 0.01", "PI                     S 3.14159265358979323846", "bakersdozen            S 13", "pair                   P 2", "ten                    P 10", "dozen                  S 12", "score                  S 20", "hundred                P 100", "thousand               P 1.0e3", "million                P 1.0e6", "", "%                      S percent", "pi                     S PI", "#", "# DIMENSIONLESS QUANTITIES", "#", "cycle                  P 1", "sample                 P 1", "trace                  P 1", "", "#", "# NB: All subsequent definitions must be given in terms of", "# earlier definitions.  Forward referencing is not permitted.", "#", "", "#", "# The following are non-base units of the fundamental quantities", "#", "", "#", "# UNITS OF ELECTRIC CURRENT", "#", "A                      S ampere", "amp                    P ampere", "abampere               P 10 ampere             # exact", "gilbert                P 7.957747e-1 ampere", "statampere             P 3.335640e-10 ampere", "biot                   P 10 ampere", "", "#", "# UNITS OF LUMINOUS INTENSITY", "#", "cd                     S candela", "candle                 P candela", "", "#", "# UNITS OF THERMODYNAMIC TEMPERATURE", "#", "degree_Kelvin          P kelvin", "degree_Celsius         S kelvin - 273.15", "degree_Rankine         P kelvin/1.8", "degree_Fahrenheit      P degree_Rankine - 459.67", "", "#C                     S degree_Celsius        # `C' means `coulomb'", "Celsius                S degree_Celsius", "celsius                S degree_Celsius", "centigrade             S degree_Celsius", "degC                   S degree_Celsius", "degreeC                S degree_Celsius", "degree_C               S degree_Celsius", "degree_c               S degree_Celsius", "degreesC               S degree_Celsius", "degrees_C              S degree_Celsius", "degrees_c              S degree_Celsius", "degrees_Celsius        S degree_Celsius", "deg_C                  S degree_Celsius", "deg_c                  S degree_Celsius", "degK                   S kelvin", "degreeK                S kelvin", "degree_K               S kelvin", "degree_k               S kelvin", "degreesK               S kelvin", "degrees_K              S kelvin", "degrees_k              S kelvin", "degrees_Kelvin         S kelvin", "deg_K                  S kelvin", "deg_k                  S kelvin", "K                      S kelvin", "Kelvin                 P kelvin", "", "degF                   S degree_Fahrenheit", "degreeF                S degree_Fahrenheit", "degree_F               S degree_Fahrenheit", "degree_f               S degree_Fahrenheit", "degreesF               S degree_Fahrenheit", "degrees_F              S degree_Fahrenheit", "degrees_f              S degree_Fahrenheit", "degrees_Fahrenheit     S degree_Fahrenheit", "deg_F                  S degree_Fahrenheit", "deg_f                  S degree_Fahrenheit", "F                      S degree_Fahrenheit", "Fahrenheit             P degree_Fahrenheit", "fahrenheit             P degree_Fahrenheit", "", "degR                   S degree_Rankine", "degreeR                S degree_Rankine", "degree_R               S degree_Rankine", "degree_r               S degree_Rankine", "degreesR               S degree_Rankine", "degrees_R              S degree_Rankine", "degrees_r              S degree_Rankine", "degrees_Rankine        S degree_Rankine", "deg_R                  S degree_Rankine", "deg_r                  S degree_Rankine", "#R                     S degree_Rankine        # `R' means `roentgen'", "Rankine                P degree_Rankine", "rankine                P degree_Rankine", "", "#", "# UNITS OF MASS", "#", "assay_ton              P 2.916667e2 kilogram", "avoirdupois_ounce      P 2.834952e-2 kilogram", "avoirdupois_pound      P 4.5359237e-1 kilogram # exact", "carat                  P 2e-4 kilogram", "grain                  P 6.479891e-5 kilogram  # exact", "gram                   P 1e-3 kilogram         # exact", "kg                     S kilogram", "long_hundredweight     P 5.080235e1 kilogram", "metric_ton             P 1e3 kilogram          # exact", "pennyweight            P 1.555174e-3 kilogram", "short_hundredweight    P 4.535924e1 kilogram", "slug                   P 14.59390 kilogram", "troy_ounce             P 3.110348e-2 kilogram", "troy_pound             P 3.732417e-1 kilogram", "atomic_mass_unit       P 1.66044e-27 kilogram", "", "tonne                  P metric_ton", "apothecary_ounce       P troy_ounce", "apothecary_pound       P avoirdupois_pound", "pound                  P avoirdupois_pound", "metricton              P metric_ton", "gr                     S grain", "scruple                P 20 grain", "apdram                 P 60 grain", "apounce                P 480 grain", "appound                P 5760 grain", "atomicmassunit         P atomic_mass_unit", "amu                    P atomic_mass_unit", "", "t                      S tonne", "lb                     P pound", "bag                    P 94 pound", "short_ton              P 2000 pound", "long_ton               P 2240 pound", "", "ton                    P short_ton", "shortton               P short_ton", "longton                P long_ton", "", "#", "# UNITS OF LENGTH", "#", "angstrom               P 1e-10 meter", "astronomical_unit      P 1.495979e11 meter", "fathom                 P 1.828804 meter", "fermi                  P 1e-15 meter           # exact", "m                      S meter", "metre                  P meter", "light_year             P 9.46055e15 meter", "micron                 P 1e-6 meter            # exact", "mil                    P 2.54e-5 meter         # exact", "nautical_mile          P 1.852000e3 meter      # exact", "parsec                 P 3.085678e16 meter", "printers_point         P 3.514598e-4 meter     # exact", "US_statute_mile        P 1.609347e3 meter      # = intn'l mile +", "#                                              #   .000003 meter", "US_survey_foot         P 3.048006e-1 meter", "chain                  P 2.011684e1 meter", "", "inch                   S 2.54 cm               # exact", "printers_pica          P 12 printers_point     # exact", "astronomicalunit       P astronomical_unit", "au                     S astronomical_unit", "nmile                  P nautical_mile", "nmi                    S nautical_mile", "", "pica                   P printers_pica", "big_point              P inch/72               # exact", "inches                 S inch", "foot                   S 12 inch               # exact", "in                     S inch", "barleycorn             P inch/3", "", "ft                     S foot", "feet                   S foot", "yard                   P 3 foot", "furlong                P 660 foot", "international_mile     P 5280 foot             # exact", "arpentlin              P 191.835 foot", "", "yd                     S yard", "rod                    P 5.5 yard", "mile                   P international_mile", "", "arpentcan              P 27.52 mile", "", "#", "# UNITS OF AMOUNT OF SUBSTANCE", "#", "mol                    S mole", "", "#", "# UNITS OF TIME", "#", "day                    P 8.64e4 second         # exact", "hour                   P 3.6e3 second          # exact", "minute                 P 60 second             # exact", "s                      S second", "sec                    P second", "shake                  P 1e-8 second           # exact", "sidereal_day           P 8.616409e4 second", "sidereal_hour          P 3.590170e3 second", "sidereal_minute        P 5.983617e1 second", "sidereal_second        P 0.9972696 second", "sidereal_year          P 3.155815e7 second", "# Interval between 2 successive passages of sun through vernal equinox", "# (365.242198781 days -- see", "# http://www.ast.cam.ac.uk/RGO/leaflets/calendar/calendar.html", "# and http://adswww.colorado.edu/adswww/astro_coord.html):", "tropical_year          P 3.15569259747e7 second", "lunar_month            P 29.530589 day", "", "common_year            P 365 day               # exact: 153600e7 s", "leap_year              P 366 day               # exact", "Julian_year            P 365.25 day            # exact", "Gregorian_year         P 365.2425 day          # exact", "sidereal_month         P 27.321661 day", "tropical_month         P 27.321582 day", "d                      S day", "min                    P minute", "hr                     P hour", "h                      S hour", "fortnight              P 14 day                # exact", "week                   P 7 day                 # exact", "", "year                   P tropical_year", "", "yr                     P year", "a                      S year                  # anno", "eon                    P 1e9 year              # fuzzy", "month                  P year/12               # on average", "", "#", "# UNITS OF PLANE ANGLE", "#", "#rad                   P radian                # `rad' means `grey'", "circle                 P 2 pi radian", "angular_degree         P (pi/180) radian", "", "turn                   P circle", "degree                 P angular_degree", "degree_north           S angular_degree", "degree_east            S angular_degree", "degree_true            S angular_degree", "arcdeg                 P angular_degree", "angular_minute         P angular_degree/60", "angular_second         P angular_minute/60", "grade                  P 0.9 angular_degree    # exact", "", "degrees_north          S degree_north", "degreeN                S degree_north", "degree_N               S degree_north", "degreesN               S degree_north", "degrees_N              S degree_north", "", "degrees_east           S degree_east", "degreeE                S degree_east", "degree_E               S degree_east", "degreesE               S degree_east", "degrees_E              S degree_east", "", "degree_west            S -1 degree_east", "degrees_west           S degree_west", "degreeW                S degree_west", "degree_W               S degree_west", "degreesW               S degree_west", "degrees_W              S degree_west", "", "degrees_true           S degree_true", "degreeT                S degree_true", "degree_T               S degree_true", "degreesT               S degree_true", "degrees_T              S degree_true", "", "arcminute              P angular_minute", "arcsecond              P angular_second", "", "arcmin                 P arcminute", "arcsec                 P arcsecond", "", "#", "# The following are derived units with special names - useful for", "# defining other derived units.", "#", "steradian              P radian^2", "hertz                  S 1/second", "newton                 P kilogram.meter/second^2", "coulomb                P ampere.second", "lumen                  P candela.steradian", "becquerel              P 1/second              # SI unit of activity of", "#                                              # a radionuclide", "standard_free_fall     S 9.806650 meter/second^2       # exact", "", "pascal                 P newton/meter^2", "joule                  P newton.meter", "hz                     S hertz", "Hz                     S hertz", "sr                     S steradian", "force                  S standard_free_fall", "gravity                S standard_free_fall", "free_fall              S standard_free_fall", "lux                    S lumen/meter^2", "sphere                 P 4 pi steradian", "", "luxes                  S lux", "watt                   P joule/second", "gray                   P joule/kilogram        # absorbed dose - derived", "sievert                P joule/kilogram        # dose equiv - derived", "mercury_32F            S gravity 13595.065 kg/m^3", "mercury_60F            S gravity 13556.806 kg/m^3", "water_39F              S gravity 999.97226 kg/m^3  # actually 39.2 F", "water_60F              S gravity 999.00072 kg/m^3", "g                      S gravity", "", "volt                   P watt/ampere", "mercury_0C             S mercury_32F", "mercury                S mercury_32F", "water                  S water_39F", "", "farad                  P coulomb/volt", "ohm                    P volt/ampere", "siemens                S ampere/volt", "weber                  P volt.second", "Hg                     S mercury", "hg                     S mercury", "H2O                    S water", "h2o                    S water", "", "tesla                  P weber/meter^2", "henry                  P weber/ampere", "", "#", "# The following are compound units: units whose definitions consist", "# of two or more base units.  They may now be defined in terms of the", "# preceding units.", "#", "", "#", "# ACCELERATION", "#", "gal                    P 1e-2 meter/second^2   # exact", "", "#", "# Area", "#", "are                    P 1e2 m^2               # exact", "barn                   P 1e-28 m^2             # exact", "circular_mil           P 5.067075e-10 m^2", "darcy                  P 9.869233e-13 m^2      # permeability of", "#                                              # porous solids", "hectare                P 1e4 m^2               # exact", "acre                   P 4840 yard^2", "", "#", "# ELECTRICITY AND MAGNETISM", "#", "abfarad                P 1e9 farad             # exact", "abhenry                P 1e-9 henry            # exact", "abmho                  P 1e9 siemens           # exact", "abohm                  P 1e-9 ohm              # exact", "abvolt                 P 1e-8 volt             # exact", "C                      S coulomb", "e                      S 1.6021917e-19 coulomb # charge of electron", "chemical_faraday       P 9.64957e4 coulomb", "physical_faraday       P 9.65219e4 coulomb", "C12_faraday            P 9.64870e4 coulomb", "gamma                  P 1e-9 tesla            # exact", "gauss                  S 1e-4 tesla            # exact", "H                      S henry", "maxwell                P 1e-8 weber            # exact", "oersted                P 7.957747e1 ampere/meter", "S                      S siemens", "statcoulomb            P 3.335640e-10 coulomb", "statfarad              P 1.112650e-12 farad", "stathenry              P 8.987554e11 henry", "statmho                P 1.112650e-12 siemens", "statohm                P 8.987554e11 ohm", "statvolt               P 2.997925e2 volt", "T                      S tesla", "unit_pole              P 1.256637e-7 weber", "V                      S volt", "Wb                     S weber", "mho                    P siemens", "Oe                     S oersted", "faraday                P C12_faraday           # charge of 1 mole of", "#                                              # electrons", "", "#", "# ENERGY (INCLUDES WORK)", "#", "electronvolt           P 1.60219e-19 joule", "erg                    P 1e-7 joule            # exact", "IT_Btu                 P 1.055056 joule        # exact", "EC_therm               P 1.05506e8 joule", "thermochemical_calorie P 4.184000 joule        # exact", "IT_calorie             P 4.1868 joule          # exact", "J                      S joule", "ton_TNT                S 4.184e9 joule", "US_therm               P 1.054804e8 joule      # exact", "watthour               P watt hour", "", "therm                  P US_therm", "Wh                     S watthour", "Btu                    P IT_Btu", "calorie                P IT_calorie", "electron_volt          P electronvolt", "", "thm                    S therm", "cal                    S calorie", "eV                     S electronvolt", "bev                    S gigaelectron_volt", "", "#", "# FORCE", "#", "dyne                   P 1e-5 newton           # exact", "pond                   P 1.806650e-3 newton    # exact", "force_kilogram         S 9.806650 newton       # exact", "force_ounce            S 2.780139e-1 newton", "force_pound            S 4.4482216152605 newton# exact", "poundal                P 1.382550e-1 newton", "N                      S newton", "gf                     S gram force", "", "force_gram             P 1e-3 force_kilogram", "force_ton              P 2000 force_pound      # exact", "lbf                    S force_pound", "ounce_force            S force_ounce", "kilogram_force         S force_kilogram", "pound_force            S force_pound", "ozf                    S force_ounce", "kgf                    S force_kilogram", "", "kip                    P 1000 lbf", "ton_force              S force_ton", "gram_force             S force_gram", "", "#", "# HEAT", "#", "clo                    P 1.55e-1 kelvin.meter^2/watt", "", "#", "# LIGHT", "#", "lm                     S lumen", "lx                     S lux", "footcandle             P 1.076391e-1 lux", "footlambert            P 3.426259 candela/meter^2", "lambert                P (1e4/PI) candela/meter^2      # exact", "stilb                  P 1e4 candela/meter^2   # exact", "phot                   P 1e4 lumen/meter^2     # exact", "nit                    P 1 candela/meter^2     # exact", "langley                P 4.184000e4 joule/meter^2      # exact", "blondel                P candela/(pi meter^2)", "", "apostilb               P blondel", "nt                     S nit", "ph                     S phot", "sb                     S stilb", "", "#", "# MASS PER UNIT LENGTH", "#", "denier                 P 1.111111e-7 kilogram/meter", "tex                    P 1e-6 kilogram/meter   # exact", "", "#", "# POWER", "#", "voltampere             P volt ampere", "VA                     S volt ampere", "boiler_horsepower      P 9.80950e3 watt", "shaft_horsepower       P 7.456999e2 watt", "metric_horsepower      P 7.35499 watt", "electric_horsepower    P 7.460000e2 watt       # exact", "W                      S watt", "water_horsepower       P 7.46043e2 watt", "UK_horsepower          P 7.4570e2 watt", "refrigeration_ton      P 12000 Btu/hour", "", "horsepower             P shaft_horsepower", "ton_of_refrigeration   P refrigeration_ton", "", "hp                     S horsepower", "", "#", "# PRESSURE OR STRESS", "#", "bar                    P 1e5 pascal            # exact", "standard_atmosphere    P 1.01325e5 pascal      # exact", "technical_atmosphere   P 1 kg gravity/cm^2     # exact", "inch_H2O_39F           S inch water_39F", "inch_H2O_60F           S inch water_60F", "inch_Hg_32F            S inch mercury_32F", "inch_Hg_60F            S inch mercury_60F", "millimeter_Hg_0C       S mm mercury_0C", "footH2O                S foot water", "cmHg                   S cm Hg", "cmH2O                  S cm water", "Pa                     S pascal", "inch_Hg                S inch Hg", "inch_hg                S inch Hg", "inHg                   S inch Hg", "in_Hg                  S inch Hg", "in_hg                  S inch Hg", "millimeter_Hg          S mm Hg", "mmHg                   S mm Hg", "mm_Hg                  S mm Hg", "mm_hg                  S mm Hg", "torr                   P mm Hg", "foot_H2O               S foot water", "ftH2O                  S foot water", "psi                    S 1 pound gravity/in^2", "ksi                    S kip/in^2", "barie                  P 0.1 newton/meter^2", "", "at                     S technical_atmosphere", "atmosphere             P standard_atmosphere", "atm                    P standard_atmosphere", "barye                  P barie", "", "#", "# MASS PER UNIT TIME (INCLUDES FLOW)", "#", "perm_0C                S 5.72135e-11 kg/(Pa.s.m^2)", "perm_23C               S 5.74525e-11 kg/(Pa.s.m^2)", "", "#", "# RADIATION UNITS", "#", "Bq                     S becquerel", "curie                  P 3.7e10 becquerel      # exact", "rem                    P 1e-2 sievert          # dose equivalent. exact", "rad                    P 1e-2 gray             # absorbed dose. exact", "roentgen               P 2.58e-4 coulomb/kg    # exact", "Sv                     S sievert", "Gy                     S gray", "", "Ci                     S curie", "R                      S roentgen", "rd                     S rad", "", "#", "# VELOCITY (INCLUDES SPEED)", "#", "c                      S 2.997925e+8 meter/sec", "knot                   P nautical_mile/hour", "", "knot_international     S knot", "international_knot     S knot", "kt                     P knot", "", "#", "# VISCOSITY", "#", "poise                  S 1e-1 pascal second    # absolute viscosity", "#                                              # exact", "stokes                 S 1e-4 meter^2/second   # exact", "rhe                    S 10/(pascal second)    # exact", "", "St                     S stokes", "", "#", "# VOLUME (INCLUDES CAPACITY)", "#", "acre_foot              S 1.233489e3 m^3", "board_foot             S 2.359737e-3 m^3", "bushel                 P 3.523907e-2 m^3", "UK_liquid_gallon       P 4.546092e-3 m^3", "Canadian_liquid_gallon P 4.546090e-3 m^3", "US_dry_gallon          P 4.404884e-3 m^3", "US_liquid_gallon       P 3.785412e-3 m^3", "cc                     S cm^3", "liter                  P 1e-3 m^3  # exact. However, from 1901 to", "#                                  # 1964, 1 liter = 1.000028 dm^3", "stere                  P 1 m^3                 # exact", "register_ton           P 3.831685 m^3", "", "US_dry_quart           P US_dry_gallon/4", "US_dry_pint            P US_dry_gallon/8", "", "US_liquid_quart        P US_liquid_gallon/4", "US_liquid_pint         P US_liquid_gallon/8", "US_liquid_cup          P US_liquid_gallon/16", "US_liquid_gill         P US_liquid_gallon/32", "US_fluid_ounce         P US_liquid_gallon/128", "US_liquid_ounce        P US_fluid_ounce", "", "UK_liquid_quart        P UK_liquid_gallon/4", "UK_liquid_pint         P UK_liquid_gallon/8", "UK_liquid_cup          P UK_liquid_gallon/16", "UK_liquid_gill         P UK_liquid_gallon/32", "UK_fluid_ounce         P UK_liquid_gallon/160", "UK_liquid_ounce        P UK_fluid_ounce", "", "liquid_gallon          P US_liquid_gallon", "fluid_ounce            P US_fluid_ounce", "#liquid_gallon         P UK_liquid_gallon", "#fluid_ounce           P UK_fluid_ounce", "", "dry_quart              P US_dry_quart", "dry_pint               P US_dry_pint", "", "liquid_quart           P liquid_gallon/4", "liquid_pint            P liquid_gallon/8", "", "gallon                 P liquid_gallon", "barrel                 P 42 US_liquid_gallon   # petroleum definition", "quart                  P liquid_quart", "pint                   P liquid_pint", "cup                    P liquid_gallon/16", "gill                   P liquid_gallon/32", "tablespoon             P US_fluid_ounce/2", "teaspoon               P tablespoon/3", "peck                   P bushel/4", "", "oz                     P fluid_ounce", "floz                   S fluid_ounce", "acre_feet              S acre_foot", "board_feet             S board_foot", "Tbl                    P tablespoon", "Tbsp                   S tablespoon", "tbsp                   S tablespoon", "Tblsp                  S tablespoon", "tblsp                  S tablespoon", "litre                  P liter", "l                      S liter", "tsp                    S teaspoon", "pk                     S peck", "bu                     S bushel", "", "fldr                   S floz/8", "dram                   P floz/16", "", "bbl                    S barrel", "firkin                 P barrel/4              # exact but barrel vague", "pt                     S pint", "dr                     S dram", "", "", "#", "# COMPUTERS AND COMMUNICATION", "#", "baud                   S 1/second              # exact", "b                      S bit", "bps                    S bit/second", "cps                    S hertz", "", "Bd                     S baud", "", "#", "# MISC", "#", "kayser                 P 1e2/meter             # exact", "rps                    S hertz", "rpm                    S hertz/60", "geopotential           S gravity", "work_year              P 2056 hours", "work_month             P work_year/12", "", "gp                     S geopotential", "dynamic                S geopotential", "" };
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/UnitsSpecs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */