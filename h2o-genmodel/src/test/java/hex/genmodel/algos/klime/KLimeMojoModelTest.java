package hex.genmodel.algos.klime;

import com.google.common.io.ByteStreams;
import hex.genmodel.MojoModel;
import hex.genmodel.MojoReaderBackend;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class KLimeMojoModelTest {

  @Test
  public void testScore0() throws Exception {
    MojoModel mojo = KLimeMojoReader.readFrom(new KLimeMojoModelTest.ClasspathReaderBackend());

    assertTrue(mojo instanceof KLimeMojoModel);

    double[][] rows = new double[][] {
            new double[]{2.0, 1.0, 22.0, 1.0, 0.0},
            new double[]{2.0, 1.0, 2.0, 3.0, 1.0},
            new double[]{2.0, 0.0, 27.0, 0.0, 2.0}
    };
    double[] preds = new double[7];

    // prediction is made by a cluster-local GLM model
    mojo.score0(rows[0], preds);
    assertEquals(preds[0], 0.127, 0.001);
    assertEquals(preds[1], 0, 0.0);

    // data point belongs to cluster 1 but prediction is made by a global model
    mojo.score0(rows[1], preds);
    assertEquals(preds[0], 0.141, 0.001);
    assertEquals(preds[1], 1, 0.0);

    // data point belongs to cluster 2 but prediction is made by a global model
    mojo.score0(rows[2], preds);
    assertEquals(preds[0], 0.596, 0.001);
    assertEquals(preds[1], 2, 0.0);
  }

  private static class ClasspathReaderBackend implements MojoReaderBackend {
    @Override
    public BufferedReader getTextFile(String filename) throws IOException {
      InputStream is = KLimeMojoModelTest.class.getResourceAsStream(filename);
      return new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public byte[] getBinaryFile(String filename) throws IOException {
      InputStream is = KLimeMojoModelTest.class.getResourceAsStream(filename);
      return ByteStreams.toByteArray(is);
    }

    @Override
    public boolean exists(String filename) {
      return true;
    }
  }

}
