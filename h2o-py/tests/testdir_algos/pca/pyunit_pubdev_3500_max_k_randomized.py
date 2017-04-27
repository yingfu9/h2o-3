from __future__ import print_function
from builtins import range
import sys

sys.path.insert(1, "../../../")
import h2o
from tests import pyunit_utils
from h2o.transforms.decomposition import H2OPCA


def pca_max_k():

    data = h2o.upload_file(pyunit_utils.locate("smalldata/prostate/prostate_cat.csv"))
    x = list(set(data.names))
    pcaGramSVD = H2OPCA(k=-1, transform="STANDARDIZE", pca_method="GramSVD", use_all_factor_levels=True,
                           impute_missing=True, max_iterations=100, seed=12345)
    pcaGramSVD.train(x, training_frame=data)
    pcaPower = H2OPCA(k=-1, transform="STANDARDIZE", pca_method="Power", use_all_factor_levels=True,
                           impute_missing=True, max_iterations=100, seed=12345)
    pcaPower.train(x, training_frame=data)
    pcaRandomized = H2OPCA(k=-1, transform="STANDARDIZE", pca_method="Randomized", use_all_factor_levels=True,
                           impute_missing=True, max_iterations=100, seed=12345)
    pcaRandomized.train(x, training_frame=data)


if __name__ == "__main__":
    pyunit_utils.standalone_test(pca_max_k)
else:
    pca_max_k()
