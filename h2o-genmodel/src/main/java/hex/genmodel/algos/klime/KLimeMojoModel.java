package hex.genmodel.algos.klime;

import hex.genmodel.MojoModel;

public class KLimeMojoModel extends MojoModel {

  MojoModel _clusteringModel;
  MojoModel _globalRegressionModel;
  MojoModel[] _clusterRegressionModels;

  KLimeMojoModel(String[] columns, String[][] domains) {
    super(columns, domains);
  }

  @Override
  public double[] score0(double[] row, double[] preds) {
    assert preds.length == row.length + 2;
    System.arraycopy(row, 0, preds, 2, row.length);
    _clusteringModel.score0(row, preds);
    int cluster = (int) preds[0];
    MojoModel regressionModel = _clusterRegressionModels[cluster] != null ?
            _clusterRegressionModels[cluster] : _globalRegressionModel;
    System.arraycopy(preds, 2, row, 0, row.length);
    regressionModel.score0(row, preds);
    preds[1] = cluster;
    return preds;
  }

}
