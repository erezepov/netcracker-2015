package ru.ncedu.java.tasks;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

/**
 * ���� ������:<br/>
 * - ����������� � ������� ���������� ������ �� ������;<br/>
 * - ��������� �������� ���������� �����-���������.<br/>
 * <br/>
 * �������<br/>
 * ��������� ���� � ������ �����, ������������ � �������� ������������������ ��������
 * ��� ����� �������� ('�' � '�' - ���� � ��� �� ������).<br/>
 * <br/>
 * ����������<br/>
 * ������ ��������� ����� ������������������ ��������, ���������� ������������
 * ����������� ��������, �������� ��������� � ��������� ����� <br/>
 * (������ ��������� ������ ����� ����� ���� ��� line feed, ��� � carriage return,
 * � � Windows ��������� ������ ��������� ��� �������: CRLF).<br/>
 * ���� � ������� ����� ��������� ���� ���� ���.<br/>
 *
 * @author Andrey Shevtsov
 */
public interface WordProcessor
{
    /**
     * @return ������� ����� ��� ������ ��� <code>null</code>,
     * ���� �� ���� �� set-������� �� ��� �������� �������.
     */
    String getText ();
    /**
     * ��������� ����� ��� ������
     * @param src ����� ��� ������
     * @throws IllegalArgumentException ���� <code>src == null</code>
     */
    void setSource (String src);
    /**
     * ��������� ����� ��� ������ �� ���������� �����
     * @param srcFile ���� �� ����� � �������
     * @throws IOException � ������ ������ ��� ������ �� �����
     * @throws IllegalArgumentException ���� <code>srcFile == null</code>
     */
    void setSourceFile (String srcFile) throws IOException;
    /**
     * ��������� ����� ��� ������ �� ���������� ������ �����
     * @param fis ����� �����
     * @throws IOException � ������ ������ ��� ������ �� ������
     * @throws IllegalArgumentException ���� <code>fis == null</code>
     */
    void setSource (FileInputStream fis) throws IOException;
    /**
     * ���� � ���������� ��� �����, ������������ � ��������� ������������������
     * �������� ��� ����� ��������. <br/>
     * ���� <code>begin</code> - ������ ������ ��� <code>null</code>,
     * �� ��������� �������� ��� �����, ��������� � �����.<br/>
     * ��� ������������ ����� ������ ���� ��������� � ������� ��������.
     * @param begin ������ ������� ������� ����
     * @return �����, ������������ � ��������� ������������������ ��������
     * @throws IllegalStateException ���� ��� ������ ��� ������
     *  (�� ���� �� set-������� �� ��� ������� ��������)
     */
    Set<String> wordsStartWith (String begin);
}