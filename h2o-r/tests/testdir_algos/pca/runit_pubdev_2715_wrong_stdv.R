setwd(normalizePath(dirname(R.utils::commandArgs(asValues=TRUE)$"f")))
source("../../../scripts/h2o-r-test-setup.R")

test.pca.stdev <- function() {
  browser()
  C <- chol(S <- toeplitz(.9 ^ (0:31))) # Cov.matrix and its root
  all.equal(S, crossprod(C))
  set.seed(17)
  X <- matrix(rnorm(32000), 1000, 32)
  Z <- X %*% C  ## ==>  cov(Z) ~=  C'C = S
  all.equal(cov(Z), S, tol = 0.08)
  hf = as.h2o(x = Z,destination_frame = "df")

  fitR <- prcomp(Z)
  fitH2O <- h2o.prcomp(hf, k = 14, max_iterations = 10000)
  Log.info("****** Comparing R PCA model and GramSVD PCA model with no standardization....")
  isFlipped1 <- checkPCAModel(fitH2O, fitR, tolerance=1, compare_all_importance=TRUE)
  fitH2Os <- h2o.prcomp(hf, k = 14, max_iterations = 10000,transform = "STANDARDIZE")
  fitRs <- prcomp(Z,center = T,scale. = T)
  Log.info("****** Comparing R PCA model and GramSVD PCA model with standardization....")
  isFlipped1 <- checkPCAModel(fitH2Os, fitRs, tolerance=1, compare_all_importance=TRUE)

}

doTest("PCA Test: prcmp data", test.pca.stdev)
