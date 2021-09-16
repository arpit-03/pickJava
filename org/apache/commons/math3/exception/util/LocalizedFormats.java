/*     */ package org.apache.commons.math3.exception.util;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum LocalizedFormats
/*     */   implements Localizable
/*     */ {
/*  43 */   ARGUMENT_OUTSIDE_DOMAIN("Argument {0} outside domain [{1} ; {2}]"),
/*  44 */   ARRAY_SIZE_EXCEEDS_MAX_VARIABLES("array size cannot be greater than {0}"),
/*  45 */   ARRAY_SIZES_SHOULD_HAVE_DIFFERENCE_1("array sizes should have difference 1 ({0} != {1} + 1)"),
/*  46 */   ARRAY_SUMS_TO_ZERO("array sums to zero"),
/*  47 */   ASSYMETRIC_EIGEN_NOT_SUPPORTED("eigen decomposition of assymetric matrices not supported yet"),
/*  48 */   AT_LEAST_ONE_COLUMN("matrix must have at least one column"),
/*  49 */   AT_LEAST_ONE_ROW("matrix must have at least one row"),
/*  50 */   BANDWIDTH("bandwidth ({0})"),
/*  51 */   BESSEL_FUNCTION_BAD_ARGUMENT("Bessel function of order {0} cannot be computed for x = {1}"),
/*  52 */   BESSEL_FUNCTION_FAILED_CONVERGENCE("Bessel function of order {0} failed to converge for x = {1}"),
/*  53 */   BINOMIAL_INVALID_PARAMETERS_ORDER("must have n >= k for binomial coefficient (n, k), got k = {0}, n = {1}"),
/*  54 */   BINOMIAL_NEGATIVE_PARAMETER("must have n >= 0 for binomial coefficient (n, k), got n = {0}"),
/*  55 */   CANNOT_CLEAR_STATISTIC_CONSTRUCTED_FROM_EXTERNAL_MOMENTS("statistics constructed from external moments cannot be cleared"),
/*  56 */   CANNOT_COMPUTE_0TH_ROOT_OF_UNITY("cannot compute 0-th root of unity, indefinite result"),
/*  57 */   CANNOT_COMPUTE_BETA_DENSITY_AT_0_FOR_SOME_ALPHA("cannot compute beta density at 0 when alpha = {0,number}"),
/*  58 */   CANNOT_COMPUTE_BETA_DENSITY_AT_1_FOR_SOME_BETA("cannot compute beta density at 1 when beta = %.3g"),
/*  59 */   CANNOT_COMPUTE_NTH_ROOT_FOR_NEGATIVE_N("cannot compute nth root for null or negative n: {0}"),
/*  60 */   CANNOT_DISCARD_NEGATIVE_NUMBER_OF_ELEMENTS("cannot discard a negative number of elements ({0})"),
/*  61 */   CANNOT_FORMAT_INSTANCE_AS_3D_VECTOR("cannot format a {0} instance as a 3D vector"),
/*  62 */   CANNOT_FORMAT_INSTANCE_AS_COMPLEX("cannot format a {0} instance as a complex number"),
/*  63 */   CANNOT_FORMAT_INSTANCE_AS_REAL_VECTOR("cannot format a {0} instance as a real vector"),
/*  64 */   CANNOT_FORMAT_OBJECT_TO_FRACTION("cannot format given object as a fraction number"),
/*  65 */   CANNOT_INCREMENT_STATISTIC_CONSTRUCTED_FROM_EXTERNAL_MOMENTS("statistics constructed from external moments cannot be incremented"),
/*  66 */   CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR("cannot normalize a zero norm vector"),
/*  67 */   CANNOT_RETRIEVE_AT_NEGATIVE_INDEX("elements cannot be retrieved from a negative array index {0}"),
/*  68 */   CANNOT_SET_AT_NEGATIVE_INDEX("cannot set an element at a negative index {0}"),
/*  69 */   CANNOT_SUBSTITUTE_ELEMENT_FROM_EMPTY_ARRAY("cannot substitute an element from an empty array"),
/*  70 */   CANNOT_TRANSFORM_TO_DOUBLE("Conversion Exception in Transformation: {0}"),
/*  71 */   CARDAN_ANGLES_SINGULARITY("Cardan angles singularity"),
/*  72 */   CLASS_DOESNT_IMPLEMENT_COMPARABLE("class ({0}) does not implement Comparable"),
/*  73 */   CLOSE_VERTICES("too close vertices near point ({0}, {1}, {2})"),
/*  74 */   CLOSEST_ORTHOGONAL_MATRIX_HAS_NEGATIVE_DETERMINANT("the closest orthogonal matrix has a negative determinant {0}"),
/*  75 */   COLUMN_INDEX_OUT_OF_RANGE("column index {0} out of allowed range [{1}, {2}]"),
/*  76 */   COLUMN_INDEX("column index ({0})"),
/*  77 */   CONSTRAINT("constraint"),
/*  78 */   CONTINUED_FRACTION_INFINITY_DIVERGENCE("Continued fraction convergents diverged to +/- infinity for value {0}"),
/*  79 */   CONTINUED_FRACTION_NAN_DIVERGENCE("Continued fraction diverged to NaN for value {0}"),
/*  80 */   CONTRACTION_CRITERIA_SMALLER_THAN_EXPANSION_FACTOR("contraction criteria ({0}) smaller than the expansion factor ({1}).  This would lead to a never ending loop of expansion and contraction as a newly expanded internal storage array would immediately satisfy the criteria for contraction."),
/*  81 */   CONTRACTION_CRITERIA_SMALLER_THAN_ONE("contraction criteria smaller than one ({0}).  This would lead to a never ending loop of expansion and contraction as an internal storage array length equal to the number of elements would satisfy the contraction criteria."),
/*  82 */   CONVERGENCE_FAILED("convergence failed"),
/*  83 */   CROSSING_BOUNDARY_LOOPS("some outline boundary loops cross each other"),
/*  84 */   CROSSOVER_RATE("crossover rate ({0})"),
/*  85 */   CUMULATIVE_PROBABILITY_RETURNED_NAN("Cumulative probability function returned NaN for argument {0} p = {1}"),
/*  86 */   DIFFERENT_ROWS_LENGTHS("some rows have length {0} while others have length {1}"),
/*  87 */   DIFFERENT_ORIG_AND_PERMUTED_DATA("original and permuted data must contain the same elements"),
/*  88 */   DIGEST_NOT_INITIALIZED("digest not initialized"),
/*  89 */   DIMENSIONS_MISMATCH_2x2("got {0}x{1} but expected {2}x{3}"),
/*  90 */   DIMENSIONS_MISMATCH_SIMPLE("{0} != {1}"),
/*  91 */   DIMENSIONS_MISMATCH("dimensions mismatch"),
/*  92 */   DISCRETE_CUMULATIVE_PROBABILITY_RETURNED_NAN("Discrete cumulative probability function returned NaN for argument {0}"),
/*  93 */   DISTRIBUTION_NOT_LOADED("distribution not loaded"),
/*  94 */   DUPLICATED_ABSCISSA_DIVISION_BY_ZERO("duplicated abscissa {0} causes division by zero"),
/*  95 */   EDGE_CONNECTED_TO_ONE_FACET("edge joining points ({0}, {1}, {2}) and ({3}, {4}, {5}) is connected to one facet only"),
/*  96 */   ELITISM_RATE("elitism rate ({0})"),
/*  97 */   EMPTY_CLUSTER_IN_K_MEANS("empty cluster in k-means"),
/*  98 */   EMPTY_INTERPOLATION_SAMPLE("sample for interpolation is empty"),
/*  99 */   EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY("empty polynomials coefficients array"),
/* 100 */   EMPTY_SELECTED_COLUMN_INDEX_ARRAY("empty selected column index array"),
/* 101 */   EMPTY_SELECTED_ROW_INDEX_ARRAY("empty selected row index array"),
/* 102 */   EMPTY_STRING_FOR_IMAGINARY_CHARACTER("empty string for imaginary character"),
/* 103 */   ENDPOINTS_NOT_AN_INTERVAL("endpoints do not specify an interval: [{0}, {1}]"),
/* 104 */   EQUAL_VERTICES_IN_SIMPLEX("equal vertices {0} and {1} in simplex configuration"),
/* 105 */   EULER_ANGLES_SINGULARITY("Euler angles singularity"),
/* 106 */   EVALUATION("evaluation"),
/* 107 */   EXPANSION_FACTOR_SMALLER_THAN_ONE("expansion factor smaller than one ({0})"),
/* 108 */   FACET_ORIENTATION_MISMATCH("facets orientation mismatch around edge joining points ({0}, {1}, {2}) and ({3}, {4}, {5})"),
/* 109 */   FACTORIAL_NEGATIVE_PARAMETER("must have n >= 0 for n!, got n = {0}"),
/* 110 */   FAILED_BRACKETING("number of iterations={4}, maximum iterations={5}, initial={6}, lower bound={7}, upper bound={8}, final a value={0}, final b value={1}, f(a)={2}, f(b)={3}"),
/* 111 */   FAILED_FRACTION_CONVERSION("Unable to convert {0} to fraction after {1} iterations"),
/* 112 */   FIRST_COLUMNS_NOT_INITIALIZED_YET("first {0} columns are not initialized yet"),
/* 113 */   FIRST_ELEMENT_NOT_ZERO("first element is not 0: {0}"),
/* 114 */   FIRST_ROWS_NOT_INITIALIZED_YET("first {0} rows are not initialized yet"),
/* 115 */   FRACTION_CONVERSION_OVERFLOW("Overflow trying to convert {0} to fraction ({1}/{2})"),
/* 116 */   FUNCTION_NOT_DIFFERENTIABLE("function is not differentiable"),
/* 117 */   FUNCTION_NOT_POLYNOMIAL("function is not polynomial"),
/* 118 */   GCD_OVERFLOW_32_BITS("overflow: gcd({0}, {1}) is 2^31"),
/* 119 */   GCD_OVERFLOW_64_BITS("overflow: gcd({0}, {1}) is 2^63"),
/* 120 */   HOLE_BETWEEN_MODELS_TIME_RANGES("{0} wide hole between models time ranges"),
/* 121 */   ILL_CONDITIONED_OPERATOR("condition number {1} is too high "),
/* 122 */   INCONSISTENT_STATE_AT_2_PI_WRAPPING("inconsistent state at 2π wrapping"),
/* 123 */   INDEX_LARGER_THAN_MAX("the index specified: {0} is larger than the current maximal index {1}"),
/* 124 */   INDEX_NOT_POSITIVE("index ({0}) is not positive"),
/* 125 */   INDEX_OUT_OF_RANGE("index {0} out of allowed range [{1}, {2}]"),
/* 126 */   INDEX("index ({0})"),
/* 127 */   NOT_FINITE_NUMBER("{0} is not a finite number"),
/* 128 */   INFINITE_BOUND("interval bounds must be finite"),
/* 129 */   ARRAY_ELEMENT("value {0} at index {1}"),
/* 130 */   INFINITE_ARRAY_ELEMENT("Array contains an infinite element, {0} at index {1}"),
/* 131 */   INFINITE_VALUE_CONVERSION("cannot convert infinite value"),
/* 132 */   INITIAL_CAPACITY_NOT_POSITIVE("initial capacity ({0}) is not positive"),
/* 133 */   INITIAL_COLUMN_AFTER_FINAL_COLUMN("initial column {1} after final column {0}"),
/* 134 */   INITIAL_ROW_AFTER_FINAL_ROW("initial row {1} after final row {0}"),
/* 135 */   INPUT_DATA_FROM_UNSUPPORTED_DATASOURCE("input data comes from unsupported datasource: {0}, supported sources: {1}, {2}"),
/*     */   
/* 137 */   INSTANCES_NOT_COMPARABLE_TO_EXISTING_VALUES("instance of class {0} not comparable to existing values"),
/* 138 */   INSUFFICIENT_DATA("insufficient data"),
/* 139 */   INSUFFICIENT_DATA_FOR_T_STATISTIC("insufficient data for t statistic, needs at least 2, got {0}"),
/* 140 */   INSUFFICIENT_DIMENSION("insufficient dimension {0}, must be at least {1}"),
/* 141 */   DIMENSION("dimension ({0})"),
/* 142 */   INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE("sample contains {0} observed points, at least {1} are required"),
/* 143 */   INSUFFICIENT_ROWS_AND_COLUMNS("insufficient data: only {0} rows and {1} columns."),
/* 144 */   INTEGRATION_METHOD_NEEDS_AT_LEAST_TWO_PREVIOUS_POINTS("multistep method needs at least {0} previous steps, got {1}"),
/* 145 */   INTERNAL_ERROR("internal error, please fill a bug report at {0}"),
/* 146 */   INVALID_BINARY_DIGIT("invalid binary digit: {0}"),
/* 147 */   INVALID_BINARY_CHROMOSOME("binary mutation works on BinaryChromosome only"),
/* 148 */   INVALID_BRACKETING_PARAMETERS("invalid bracketing parameters:  lower bound={0},  initial={1}, upper bound={2}"),
/* 149 */   INVALID_FIXED_LENGTH_CHROMOSOME("one-point crossover only works with fixed-length chromosomes"),
/* 150 */   INVALID_IMPLEMENTATION("required functionality is missing in {0}"),
/* 151 */   INVALID_INTERVAL_INITIAL_VALUE_PARAMETERS("invalid interval, initial value parameters:  lower={0}, initial={1}, upper={2}"),
/* 152 */   INVALID_ITERATIONS_LIMITS("invalid iteration limits: min={0}, max={1}"),
/* 153 */   INVALID_MAX_ITERATIONS("bad value for maximum iterations number: {0}"),
/* 154 */   NOT_ENOUGH_DATA_REGRESSION("the number of observations is not sufficient to conduct regression"),
/* 155 */   INVALID_REGRESSION_ARRAY("input data array length = {0} does not match the number of observations = {1} and the number of regressors = {2}"),
/* 156 */   INVALID_REGRESSION_OBSERVATION("length of regressor array = {0} does not match the number of variables = {1} in the model"),
/* 157 */   INVALID_ROUNDING_METHOD("invalid rounding method {0}, valid methods: {1} ({2}), {3} ({4}), {5} ({6}), {7} ({8}), {9} ({10}), {11} ({12}), {13} ({14}), {15} ({16})"),
/* 158 */   ITERATOR_EXHAUSTED("iterator exhausted"),
/* 159 */   ITERATIONS("iterations"),
/* 160 */   LCM_OVERFLOW_32_BITS("overflow: lcm({0}, {1}) is 2^31"),
/* 161 */   LCM_OVERFLOW_64_BITS("overflow: lcm({0}, {1}) is 2^63"),
/* 162 */   LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE("list of chromosomes bigger than maxPopulationSize"),
/* 163 */   LOESS_EXPECTS_AT_LEAST_ONE_POINT("Loess expects at least 1 point"),
/* 164 */   LOWER_BOUND_NOT_BELOW_UPPER_BOUND("lower bound ({0}) must be strictly less than upper bound ({1})"),
/* 165 */   LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT("lower endpoint ({0}) must be less than or equal to upper endpoint ({1})"),
/* 166 */   MAP_MODIFIED_WHILE_ITERATING("map has been modified while iterating"),
/* 167 */   MULTISTEP_STARTER_STOPPED_EARLY("multistep integrator starter stopped early, maybe too large step size"),
/* 168 */   EVALUATIONS("evaluations"),
/* 169 */   MAX_COUNT_EXCEEDED("maximal count ({0}) exceeded"),
/* 170 */   MAX_ITERATIONS_EXCEEDED("maximal number of iterations ({0}) exceeded"),
/* 171 */   MINIMAL_STEPSIZE_REACHED_DURING_INTEGRATION("minimal step size ({1,number,0.00E00}) reached, integration needs {0,number,0.00E00}"),
/* 172 */   MISMATCHED_LOESS_ABSCISSA_ORDINATE_ARRAYS("Loess expects the abscissa and ordinate arrays to be of the same size, but got {0} abscissae and {1} ordinatae"),
/* 173 */   MUTATION_RATE("mutation rate ({0})"),
/* 174 */   NAN_ELEMENT_AT_INDEX("element {0} is NaN"),
/* 175 */   NAN_VALUE_CONVERSION("cannot convert NaN value"),
/* 176 */   NEGATIVE_BRIGHTNESS_EXPONENT("brightness exponent should be positive or null, but got {0}"),
/* 177 */   NEGATIVE_COMPLEX_MODULE("negative complex module {0}"),
/* 178 */   NEGATIVE_ELEMENT_AT_2D_INDEX("element ({0}, {1}) is negative: {2}"),
/* 179 */   NEGATIVE_ELEMENT_AT_INDEX("element {0} is negative: {1}"),
/* 180 */   NEGATIVE_NUMBER_OF_SUCCESSES("number of successes must be non-negative ({0})"),
/* 181 */   NUMBER_OF_SUCCESSES("number of successes ({0})"),
/* 182 */   NEGATIVE_NUMBER_OF_TRIALS("number of trials must be non-negative ({0})"),
/* 183 */   NUMBER_OF_INTERPOLATION_POINTS("number of interpolation points ({0})"),
/* 184 */   NUMBER_OF_TRIALS("number of trials ({0})"),
/* 185 */   NOT_CONVEX("vertices do not form a convex hull in CCW winding"),
/* 186 */   NOT_CONVEX_HYPERPLANES("hyperplanes do not define a convex region"),
/* 187 */   ROBUSTNESS_ITERATIONS("number of robustness iterations ({0})"),
/* 188 */   START_POSITION("start position ({0})"),
/* 189 */   NON_CONVERGENT_CONTINUED_FRACTION("Continued fraction convergents failed to converge (in less than {0} iterations) for value {1}"),
/* 190 */   NON_INVERTIBLE_TRANSFORM("non-invertible affine transform collapses some lines into single points"),
/* 191 */   NON_POSITIVE_MICROSPHERE_ELEMENTS("number of microsphere elements must be positive, but got {0}"),
/* 192 */   NON_POSITIVE_POLYNOMIAL_DEGREE("polynomial degree must be positive: degree={0}"),
/* 193 */   NON_REAL_FINITE_ABSCISSA("all abscissae must be finite real numbers, but {0}-th is {1}"),
/* 194 */   NON_REAL_FINITE_ORDINATE("all ordinatae must be finite real numbers, but {0}-th is {1}"),
/* 195 */   NON_REAL_FINITE_WEIGHT("all weights must be finite real numbers, but {0}-th is {1}"),
/* 196 */   NON_SQUARE_MATRIX("non square ({0}x{1}) matrix"),
/* 197 */   NORM("Norm ({0})"),
/* 198 */   NORMALIZE_INFINITE("Cannot normalize to an infinite value"),
/* 199 */   NORMALIZE_NAN("Cannot normalize to NaN"),
/* 200 */   NOT_ADDITION_COMPATIBLE_MATRICES("{0}x{1} and {2}x{3} matrices are not addition compatible"),
/* 201 */   NOT_DECREASING_NUMBER_OF_POINTS("points {0} and {1} are not decreasing ({2} < {3})"),
/* 202 */   NOT_DECREASING_SEQUENCE("points {3} and {2} are not decreasing ({1} < {0})"),
/* 203 */   NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS("not enough data ({0} rows) for this many predictors ({1} predictors)"),
/* 204 */   NOT_ENOUGH_POINTS_IN_SPLINE_PARTITION("spline partition must have at least {0} points, got {1}"),
/* 205 */   NOT_INCREASING_NUMBER_OF_POINTS("points {0} and {1} are not increasing ({2} > {3})"),
/* 206 */   NOT_INCREASING_SEQUENCE("points {3} and {2} are not increasing ({1} > {0})"),
/* 207 */   NOT_MULTIPLICATION_COMPATIBLE_MATRICES("{0}x{1} and {2}x{3} matrices are not multiplication compatible"),
/* 208 */   NOT_POSITIVE_DEFINITE_MATRIX("not positive definite matrix"),
/* 209 */   NON_POSITIVE_DEFINITE_MATRIX("not positive definite matrix: diagonal element at ({1},{1}) is smaller than {2} ({0})"),
/* 210 */   NON_POSITIVE_DEFINITE_OPERATOR("non positive definite linear operator"),
/* 211 */   NON_SELF_ADJOINT_OPERATOR("non self-adjoint linear operator"),
/* 212 */   NON_SQUARE_OPERATOR("non square ({0}x{1}) linear operator"),
/* 213 */   DEGREES_OF_FREEDOM("degrees of freedom ({0})"),
/* 214 */   NOT_POSITIVE_DEGREES_OF_FREEDOM("degrees of freedom must be positive ({0})"),
/* 215 */   NOT_POSITIVE_ELEMENT_AT_INDEX("element {0} is not positive: {1}"),
/* 216 */   NOT_POSITIVE_EXPONENT("invalid exponent {0} (must be positive)"),
/* 217 */   NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE("number of elements should be positive ({0})"),
/* 218 */   BASE("base ({0})"),
/* 219 */   EXPONENT("exponent ({0})"),
/* 220 */   NOT_POSITIVE_LENGTH("length must be positive ({0})"),
/* 221 */   LENGTH("length ({0})"),
/* 222 */   NOT_POSITIVE_MEAN("mean must be positive ({0})"),
/* 223 */   MEAN("mean ({0})"),
/* 224 */   NOT_POSITIVE_NUMBER_OF_SAMPLES("number of sample is not positive: {0}"),
/* 225 */   NUMBER_OF_SAMPLES("number of samples ({0})"),
/* 226 */   NOT_POSITIVE_PERMUTATION("permutation k ({0}) must be positive"),
/* 227 */   PERMUTATION_SIZE("permutation size ({0}"),
/* 228 */   NOT_POSITIVE_POISSON_MEAN("the Poisson mean must be positive ({0})"),
/* 229 */   NOT_POSITIVE_POPULATION_SIZE("population size must be positive ({0})"),
/* 230 */   POPULATION_SIZE("population size ({0})"),
/* 231 */   NOT_POSITIVE_ROW_DIMENSION("invalid row dimension: {0} (must be positive)"),
/* 232 */   NOT_POSITIVE_SAMPLE_SIZE("sample size must be positive ({0})"),
/* 233 */   NOT_POSITIVE_SCALE("scale must be positive ({0})"),
/* 234 */   SCALE("scale ({0})"),
/* 235 */   NOT_POSITIVE_SHAPE("shape must be positive ({0})"),
/* 236 */   SHAPE("shape ({0})"),
/* 237 */   NOT_POSITIVE_STANDARD_DEVIATION("standard deviation must be positive ({0})"),
/* 238 */   STANDARD_DEVIATION("standard deviation ({0})"),
/* 239 */   NOT_POSITIVE_UPPER_BOUND("upper bound must be positive ({0})"),
/* 240 */   NOT_POSITIVE_WINDOW_SIZE("window size must be positive ({0})"),
/* 241 */   NOT_POWER_OF_TWO("{0} is not a power of 2"),
/* 242 */   NOT_POWER_OF_TWO_CONSIDER_PADDING("{0} is not a power of 2, consider padding for fix"),
/* 243 */   NOT_POWER_OF_TWO_PLUS_ONE("{0} is not a power of 2 plus one"),
/* 244 */   NOT_STRICTLY_DECREASING_NUMBER_OF_POINTS("points {0} and {1} are not strictly decreasing ({2} <= {3})"),
/* 245 */   NOT_STRICTLY_DECREASING_SEQUENCE("points {3} and {2} are not strictly decreasing ({1} <= {0})"),
/* 246 */   NOT_STRICTLY_INCREASING_KNOT_VALUES("knot values must be strictly increasing"),
/* 247 */   NOT_STRICTLY_INCREASING_NUMBER_OF_POINTS("points {0} and {1} are not strictly increasing ({2} >= {3})"),
/* 248 */   NOT_STRICTLY_INCREASING_SEQUENCE("points {3} and {2} are not strictly increasing ({1} >= {0})"),
/* 249 */   NOT_SUBTRACTION_COMPATIBLE_MATRICES("{0}x{1} and {2}x{3} matrices are not subtraction compatible"),
/* 250 */   NOT_SUPPORTED_IN_DIMENSION_N("method not supported in dimension {0}"),
/* 251 */   NOT_SYMMETRIC_MATRIX("not symmetric matrix"),
/* 252 */   NON_SYMMETRIC_MATRIX("non symmetric matrix: the difference between entries at ({0},{1}) and ({1},{0}) is larger than {2}"),
/* 253 */   NO_BIN_SELECTED("no bin selected"),
/* 254 */   NO_CONVERGENCE_WITH_ANY_START_POINT("none of the {0} start points lead to convergence"),
/* 255 */   NO_DATA("no data"),
/* 256 */   NO_DEGREES_OF_FREEDOM("no degrees of freedom ({0} measurements, {1} parameters)"),
/* 257 */   NO_DENSITY_FOR_THIS_DISTRIBUTION("This distribution does not have a density function implemented"),
/* 258 */   NO_FEASIBLE_SOLUTION("no feasible solution"),
/* 259 */   NO_OPTIMUM_COMPUTED_YET("no optimum computed yet"),
/* 260 */   NO_REGRESSORS("Regression model must include at least one regressor"),
/* 261 */   NO_RESULT_AVAILABLE("no result available"),
/* 262 */   NO_SUCH_MATRIX_ENTRY("no entry at indices ({0}, {1}) in a {2}x{3} matrix"),
/* 263 */   NAN_NOT_ALLOWED("NaN is not allowed"),
/* 264 */   NULL_NOT_ALLOWED("null is not allowed"),
/* 265 */   ARRAY_ZERO_LENGTH_OR_NULL_NOT_ALLOWED("a null or zero length array not allowed"),
/* 266 */   COVARIANCE_MATRIX("covariance matrix"),
/* 267 */   DENOMINATOR("denominator"),
/* 268 */   DENOMINATOR_FORMAT("denominator format"),
/* 269 */   FRACTION("fraction"),
/* 270 */   FUNCTION("function"),
/* 271 */   IMAGINARY_FORMAT("imaginary format"),
/* 272 */   INPUT_ARRAY("input array"),
/* 273 */   NUMERATOR("numerator"),
/* 274 */   NUMERATOR_FORMAT("numerator format"),
/* 275 */   OBJECT_TRANSFORMATION("conversion exception in transformation"),
/* 276 */   REAL_FORMAT("real format"),
/* 277 */   WHOLE_FORMAT("whole format"),
/* 278 */   NUMBER_TOO_LARGE("{0} is larger than the maximum ({1})"),
/* 279 */   NUMBER_TOO_SMALL("{0} is smaller than the minimum ({1})"),
/* 280 */   NUMBER_TOO_LARGE_BOUND_EXCLUDED("{0} is larger than, or equal to, the maximum ({1})"),
/* 281 */   NUMBER_TOO_SMALL_BOUND_EXCLUDED("{0} is smaller than, or equal to, the minimum ({1})"),
/* 282 */   NUMBER_OF_SUCCESS_LARGER_THAN_POPULATION_SIZE("number of successes ({0}) must be less than or equal to population size ({1})"),
/* 283 */   NUMERATOR_OVERFLOW_AFTER_MULTIPLY("overflow, numerator too large after multiply: {0}"),
/* 284 */   N_POINTS_GAUSS_LEGENDRE_INTEGRATOR_NOT_SUPPORTED("{0} points Legendre-Gauss integrator not supported, number of points must be in the {1}-{2} range"),
/* 285 */   OBSERVED_COUNTS_ALL_ZERO("observed counts are all 0 in observed array {0}"),
/* 286 */   OBSERVED_COUNTS_BOTTH_ZERO_FOR_ENTRY("observed counts are both zero for entry {0}"),
/* 287 */   BOBYQA_BOUND_DIFFERENCE_CONDITION("the difference between the upper and lower bound must be larger than twice the initial trust region radius ({0})"),
/* 288 */   OUT_OF_BOUNDS_QUANTILE_VALUE("out of bounds quantile value: {0}, must be in (0, 100]"),
/* 289 */   OUT_OF_BOUNDS_CONFIDENCE_LEVEL("out of bounds confidence level {0}, must be between {1} and {2}"),
/* 290 */   OUT_OF_BOUND_SIGNIFICANCE_LEVEL("out of bounds significance level {0}, must be between {1} and {2}"),
/* 291 */   SIGNIFICANCE_LEVEL("significance level ({0})"),
/* 292 */   OUT_OF_ORDER_ABSCISSA_ARRAY("the abscissae array must be sorted in a strictly increasing order, but the {0}-th element is {1} whereas {2}-th is {3}"),
/* 293 */   OUT_OF_PLANE("point ({0}, {1}, {2}) is out of plane"),
/* 294 */   OUT_OF_RANGE_ROOT_OF_UNITY_INDEX("out of range root of unity index {0} (must be in [{1};{2}])"),
/* 295 */   OUT_OF_RANGE("out of range"),
/* 296 */   OUT_OF_RANGE_SIMPLE("{0} out of [{1}, {2}] range"),
/* 297 */   OUT_OF_RANGE_LEFT("{0} out of ({1}, {2}] range"),
/* 298 */   OUT_OF_RANGE_RIGHT("{0} out of [{1}, {2}) range"),
/* 299 */   OUTLINE_BOUNDARY_LOOP_OPEN("an outline boundary loop is open"),
/* 300 */   OVERFLOW("overflow"),
/* 301 */   OVERFLOW_IN_FRACTION("overflow in fraction {0}/{1}, cannot negate"),
/* 302 */   OVERFLOW_IN_ADDITION("overflow in addition: {0} + {1}"),
/* 303 */   OVERFLOW_IN_SUBTRACTION("overflow in subtraction: {0} - {1}"),
/* 304 */   OVERFLOW_IN_MULTIPLICATION("overflow in multiplication: {0} * {1}"),
/* 305 */   PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD("cannot access {0} method in percentile implementation {1}"),
/* 306 */   PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD("percentile implementation {0} does not support {1}"),
/* 307 */   PERMUTATION_EXCEEDS_N("permutation size ({0}) exceeds permuation domain ({1})"),
/* 308 */   POLYNOMIAL("polynomial"),
/* 309 */   POLYNOMIAL_INTERPOLANTS_MISMATCH_SEGMENTS("number of polynomial interpolants must match the number of segments ({0} != {1} - 1)"),
/* 310 */   POPULATION_LIMIT_NOT_POSITIVE("population limit has to be positive"),
/* 311 */   POWER_NEGATIVE_PARAMETERS("cannot raise an integral value to a negative power ({0}^{1})"),
/* 312 */   PROPAGATION_DIRECTION_MISMATCH("propagation direction mismatch"),
/* 313 */   RANDOMKEY_MUTATION_WRONG_CLASS("RandomKeyMutation works only with RandomKeys, not {0}"),
/* 314 */   ROOTS_OF_UNITY_NOT_COMPUTED_YET("roots of unity have not been computed yet"),
/* 315 */   ROTATION_MATRIX_DIMENSIONS("a {0}x{1} matrix cannot be a rotation matrix"),
/* 316 */   ROW_INDEX_OUT_OF_RANGE("row index {0} out of allowed range [{1}, {2}]"),
/* 317 */   ROW_INDEX("row index ({0})"),
/* 318 */   SAME_SIGN_AT_ENDPOINTS("function values at endpoints do not have different signs, endpoints: [{0}, {1}], values: [{2}, {3}]"),
/* 319 */   SAMPLE_SIZE_EXCEEDS_COLLECTION_SIZE("sample size ({0}) exceeds collection size ({1})"),
/* 320 */   SAMPLE_SIZE_LARGER_THAN_POPULATION_SIZE("sample size ({0}) must be less than or equal to population size ({1})"),
/* 321 */   SIMPLEX_NEED_ONE_POINT("simplex must contain at least one point"),
/* 322 */   SIMPLE_MESSAGE("{0}"),
/* 323 */   SINGULAR_MATRIX("matrix is singular"),
/* 324 */   SINGULAR_OPERATOR("operator is singular"),
/* 325 */   SUBARRAY_ENDS_AFTER_ARRAY_END("subarray ends after array end"),
/* 326 */   TOO_LARGE_CUTOFF_SINGULAR_VALUE("cutoff singular value is {0}, should be at most {1}"),
/* 327 */   TOO_LARGE_TOURNAMENT_ARITY("tournament arity ({0}) cannot be bigger than population size ({1})"),
/* 328 */   TOO_MANY_ELEMENTS_TO_DISCARD_FROM_ARRAY("cannot discard {0} elements from a {1} elements array"),
/* 329 */   TOO_MANY_REGRESSORS("too many regressors ({0}) specified, only {1} in the model"),
/* 330 */   TOO_SMALL_COST_RELATIVE_TOLERANCE("cost relative tolerance is too small ({0}), no further reduction in the sum of squares is possible"),
/* 331 */   TOO_SMALL_INTEGRATION_INTERVAL("too small integration interval: length = {0}"),
/* 332 */   TOO_SMALL_ORTHOGONALITY_TOLERANCE("orthogonality tolerance is too small ({0}), solution is orthogonal to the jacobian"),
/* 333 */   TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE("parameters relative tolerance is too small ({0}), no further improvement in the approximate solution is possible"),
/* 334 */   TRUST_REGION_STEP_FAILED("trust region step has failed to reduce Q"),
/* 335 */   TWO_OR_MORE_CATEGORIES_REQUIRED("two or more categories required, got {0}"),
/* 336 */   TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED("two or more values required in each category, one has {0}"),
/* 337 */   UNABLE_TO_BRACKET_OPTIMUM_IN_LINE_SEARCH("unable to bracket optimum in line search"),
/* 338 */   UNABLE_TO_COMPUTE_COVARIANCE_SINGULAR_PROBLEM("unable to compute covariances: singular problem"),
/* 339 */   UNABLE_TO_FIRST_GUESS_HARMONIC_COEFFICIENTS("unable to first guess the harmonic coefficients"),
/* 340 */   UNABLE_TO_ORTHOGONOLIZE_MATRIX("unable to orthogonalize matrix in {0} iterations"),
/* 341 */   UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN("unable to perform Q.R decomposition on the {0}x{1} jacobian matrix"),
/* 342 */   UNABLE_TO_SOLVE_SINGULAR_PROBLEM("unable to solve: singular problem"),
/* 343 */   UNBOUNDED_SOLUTION("unbounded solution"),
/* 344 */   UNKNOWN_MODE("unknown mode {0}, known modes: {1} ({2}), {3} ({4}), {5} ({6}), {7} ({8}), {9} ({10}) and {11} ({12})"),
/* 345 */   UNKNOWN_PARAMETER("unknown parameter {0}"),
/* 346 */   UNMATCHED_ODE_IN_EXPANDED_SET("ode does not match the main ode set in the extended set"),
/* 347 */   CANNOT_PARSE_AS_TYPE("string \"{0}\" unparseable (from position {1}) as an object of type {2}"),
/* 348 */   CANNOT_PARSE("string \"{0}\" unparseable (from position {1})"),
/* 349 */   UNPARSEABLE_3D_VECTOR("unparseable 3D vector: \"{0}\""),
/* 350 */   UNPARSEABLE_COMPLEX_NUMBER("unparseable complex number: \"{0}\""),
/* 351 */   UNPARSEABLE_REAL_VECTOR("unparseable real vector: \"{0}\""),
/* 352 */   UNSUPPORTED_EXPANSION_MODE("unsupported expansion mode {0}, supported modes are {1} ({2}) and {3} ({4})"),
/* 353 */   UNSUPPORTED_OPERATION("unsupported operation"),
/* 354 */   ARITHMETIC_EXCEPTION("arithmetic exception"),
/* 355 */   ILLEGAL_STATE("illegal state"),
/* 356 */   USER_EXCEPTION("exception generated in user code"),
/* 357 */   URL_CONTAINS_NO_DATA("URL {0} contains no data"),
/* 358 */   VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC("{0} values have been added before statistic is configured"),
/* 359 */   VECTOR_LENGTH_MISMATCH("vector length mismatch: got {0} but expected {1}"),
/* 360 */   VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT("vector must have at least one element"),
/* 361 */   WEIGHT_AT_LEAST_ONE_NON_ZERO("weigth array must contain at least one non-zero value"),
/* 362 */   WRONG_BLOCK_LENGTH("wrong array shape (block length = {0}, expected {1})"),
/* 363 */   WRONG_NUMBER_OF_POINTS("{0} points are required, got only {1}"),
/* 364 */   NUMBER_OF_POINTS("number of points ({0})"),
/* 365 */   ZERO_DENOMINATOR("denominator must be different from 0"),
/* 366 */   ZERO_DENOMINATOR_IN_FRACTION("zero denominator in fraction {0}/{1}"),
/* 367 */   ZERO_FRACTION_TO_DIVIDE_BY("the fraction to divide by must not be zero: {0}/{1}"),
/* 368 */   ZERO_NORM("zero norm"),
/* 369 */   ZERO_NORM_FOR_ROTATION_AXIS("zero norm for rotation axis"),
/* 370 */   ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR("zero norm for rotation defining vector"),
/* 371 */   ZERO_NOT_ALLOWED("zero not allowed here");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String sourceFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LocalizedFormats(String sourceFormat) {
/* 385 */     this.sourceFormat = sourceFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceString() {
/* 390 */     return this.sourceFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalizedString(Locale locale) {
/*     */     try {
/* 396 */       String path = LocalizedFormats.class.getName().replaceAll("\\.", "/");
/* 397 */       ResourceBundle bundle = ResourceBundle.getBundle("assets/" + path, locale);
/*     */       
/* 399 */       if (bundle.getLocale().getLanguage().equals(locale.getLanguage()))
/*     */       {
/* 401 */         return bundle.getString(toString());
/*     */       }
/*     */     }
/* 404 */     catch (MissingResourceException missingResourceException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     return this.sourceFormat;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/util/LocalizedFormats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */