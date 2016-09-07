package org.apache.flink.examples.java.ml.util;

/**
 * Contains all static variables used by L-BFGS algorithm and its subroutines.
 */
public class Statics{
	private int infoc[] = new int[1], j = 0;
	private double dg = 0, dgm = 0, dginit = 0, dgtest = 0, dgx[] = new double[1], dgxm[] = new double[1], dgy[] = new double[1], dgym[] = new double[1], finit = 0, ftest1 = 0, fm = 0, fx[] = new double[1], fxm[] = new double[1], fy[] = new double[1], fym[] = new double[1], p5 = 0, p66 = 0, stx[] = new double[1], sty[] = new double[1], stmin = 0, stmax = 0, width = 0, width1 = 0, xtrapf = 0;
	private boolean brackt[] = new boolean[1], stage1 = false;

	public Statics() {
	}

	public Statics(int[] infoc, int j, double dg, double dgm, double dginit, double dgtest, double[] dgx, double[] dgxm, double[] dgy, double[] dgym, double finit, double ftest1, double fm, double[] fx, double[] fxm, double[] fy, double[] fym, double p5, double p66, double[] stx, double[] sty, double stmin, double stmax, double width, double width1, double xtrapf, boolean[] brackt, boolean stage1) {
		this.infoc = infoc;
		this.j = j;
		this.dg = dg;
		this.dgm = dgm;
		this.dginit = dginit;
		this.dgtest = dgtest;
		this.dgx = dgx;
		this.dgxm = dgxm;
		this.dgy = dgy;
		this.dgym = dgym;
		this.finit = finit;
		this.ftest1 = ftest1;
		this.fm = fm;
		this.fx = fx;
		this.fxm = fxm;
		this.fy = fy;
		this.fym = fym;
		this.p5 = p5;
		this.p66 = p66;
		this.stx = stx;
		this.sty = sty;
		this.stmin = stmin;
		this.stmax = stmax;
		this.width = width;
		this.width1 = width1;
		this.xtrapf = xtrapf;
		this.brackt = brackt;
		this.stage1 = stage1;
	}

	public int[] getInfoc() {
		return infoc;
	}

	public void setInfoc(int[] infoc) {
		this.infoc = infoc;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public double getDg() {
		return dg;
	}

	public void setDg(double dg) {
		this.dg = dg;
	}

	public double getDgm() {
		return dgm;
	}

	public void setDgm(double dgm) {
		this.dgm = dgm;
	}

	public double getDginit() {
		return dginit;
	}

	public void setDginit(double dginit) {
		this.dginit = dginit;
	}

	public double getDgtest() {
		return dgtest;
	}

	public void setDgtest(double dgtest) {
		this.dgtest = dgtest;
	}

	public double[] getDgx() {
		return dgx;
	}

	public void setDgx(double[] dgx) {
		this.dgx = dgx;
	}

	public double[] getDgxm() {
		return dgxm;
	}

	public void setDgxm(double[] dgxm) {
		this.dgxm = dgxm;
	}

	public double[] getDgy() {
		return dgy;
	}

	public void setDgy(double[] dgy) {
		this.dgy = dgy;
	}

	public double[] getDgym() {
		return dgym;
	}

	public void setDgym(double[] dgym) {
		this.dgym = dgym;
	}

	public double getFinit() {
		return finit;
	}

	public void setFinit(double finit) {
		this.finit = finit;
	}

	public double getFtest1() {
		return ftest1;
	}

	public void setFtest1(double ftest1) {
		this.ftest1 = ftest1;
	}

	public double getFm() {
		return fm;
	}

	public void setFm(double fm) {
		this.fm = fm;
	}

	public double[] getFx() {
		return fx;
	}

	public void setFx(double[] fx) {
		this.fx = fx;
	}

	public double[] getFxm() {
		return fxm;
	}

	public void setFxm(double[] fxm) {
		this.fxm = fxm;
	}

	public double[] getFy() {
		return fy;
	}

	public void setFy(double[] fy) {
		this.fy = fy;
	}

	public double[] getFym() {
		return fym;
	}

	public void setFym(double[] fym) {
		this.fym = fym;
	}

	public double getP5() {
		return p5;
	}

	public void setP5(double p5) {
		this.p5 = p5;
	}

	public double getP66() {
		return p66;
	}

	public void setP66(double p66) {
		this.p66 = p66;
	}

	public double[] getStx() {
		return stx;
	}

	public void setStx(double[] stx) {
		this.stx = stx;
	}

	public double[] getSty() {
		return sty;
	}

	public void setSty(double[] sty) {
		this.sty = sty;
	}

	public double getStmin() {
		return stmin;
	}

	public void setStmin(double stmin) {
		this.stmin = stmin;
	}

	public double getStmax() {
		return stmax;
	}

	public void setStmax(double stmax) {
		this.stmax = stmax;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidth1() {
		return width1;
	}

	public void setWidth1(double width1) {
		this.width1 = width1;
	}

	public double getXtrapf() {
		return xtrapf;
	}

	public void setXtrapf(double xtrapf) {
		this.xtrapf = xtrapf;
	}

	public boolean[] getBrackt() {
		return brackt;
	}

	public void setBrackt(boolean[] brackt) {
		this.brackt = brackt;
	}

	public boolean isStage1() {
		return stage1;
	}

	public void setStage1(boolean stage1) {
		this.stage1 = stage1;
	}
}

